package com.home.net;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Socket NIO Channel Selector ThreadPool
 * 完成文件传输
 * @author: xu.dm
 * @since: 2025/8/27 13:38
 **/
public class SelectorThreadServer {
    private Selector selector;
    private ServerSocketChannel serverChannel;
    private final int port;
    private final ExecutorService executor = new ThreadPoolExecutor(
            8,           // 核心线程数
            80,          // 最大线程数
            60L,          // 空闲线程存活时间
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(500), // 任务队列大小
            new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
    );

    public static void main(String[] args) {
        try {
            SelectorThreadServer server = new SelectorThreadServer(8080);
            server.start();
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public SelectorThreadServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        // 1. 创建 Selector
        selector = Selector.open();

        // 2. 创建 ServerSocketChannel
        serverChannel = ServerSocketChannel.open();

        // 3. 配置为非阻塞模式
        serverChannel.configureBlocking(false);

        // 4. 绑定端口
        serverChannel.bind(new InetSocketAddress(port));

        // 5. 将 ServerSocketChannel 注册到 Selector，监听 OP_ACCEPT 事件
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("File Server started on port " + port);

        // 6. 主循环处理事件
        while (true) {
            // 等待事件发生，阻塞直到至少有一个通道就绪
            int readyChannels = selector.select();

            if (readyChannels == 0) {
                continue;
            }

            // 获取就绪的 SelectionKey 集合
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            // 处理所有就绪的事件
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                try {
                    if (key.isAcceptable()) {
                        handleAccept(key);
                    } else if (key.isReadable()) {
                        handleRead(key);
                    } else if (key.isWritable()) {
                        handleWrite(key);
                    }
                } catch (IOException e) {
                    System.err.println("Error handling client: " + e.getMessage());
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException ignored) {
                    }
                }

                keyIterator.remove();
            }
        }
    }

    // 处理新的连接请求
    private void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();

        if (clientChannel != null) {
            clientChannel.configureBlocking(false);

            // 为新连接创建客户端上下文
            ClientContext context = new ClientContext();

            // 注册到 Selector，监听 OP_READ 事件
            // 绑定此链接的上下文context
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ, context);

            System.out.println("New client connected: " + clientChannel.getRemoteAddress());
        }
    }

    // 处理读事件
    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ClientContext context = (ClientContext) key.attachment();

        ByteBuffer buffer = context.buffer;
        buffer.clear();
        int bytesRead = clientChannel.read(buffer);

        System.out.println("handleRead bytes read: " + bytesRead);
        if (bytesRead == -1 || context.transmissionComplete) {
            handleClientDisconnect(key, clientChannel, context);
            return;
        }

        if (bytesRead > 0) {
            // 切换 buffer 为读模式
            buffer.flip();

            // 如果尚未接收文件名
            if (!context.fileNameReceived) {
                processFileName(key);
            } else {
                byte[] currentData = new byte[buffer.remaining()];
                buffer.get(currentData);
                buffer.clear();

                // 添加到队列
                if(currentData.length > 0){
                    context.dataQueue.offer(currentData);
                }

                // 如果没有在处理，则提交任务
                if (!context.processing) {
                    context.processing = true;
                    executor.submit(() -> processFileDataQueue(key, context));
                }
            }
        }
    }


    // 处理队列中的数据
    private void processFileDataQueue(SelectionKey key, ClientContext context) {
        try {
            byte[] data;
            while ((data = context.dataQueue.poll()) != null) {
                // 处理数据逻辑
                processSingleDataChunk(key, context, data);
            }

            // 处理完成后标记为未处理状态
            context.processing = false;

            // 检查是否传输已完成且需要发送响应
            if (context.transmissionComplete) {
                // 准备发送确认响应
                String response = "File " + context.fileName + " transfer completed, Received " + context.receivedBytes + " bytes\n";
                ByteBuffer wrap = ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8));
                context.writeBuffer.clear();
                context.writeBuffer.put(wrap);
                context.writeBuffer.flip();

                // 修改关注事件为 OP_WRITE，准备写数据
                key.interestOps(SelectionKey.OP_WRITE);
                selector.wakeup(); // 唤醒selector
                System.out.println("Response prepared and OP_WRITE set");
            }

            // 检查是否还有新数据需要处理
            if (!context.dataQueue.isEmpty()) {
                context.processing = true;
                executor.submit(() -> processFileDataQueue(key, context));
            }
        } catch (Exception e) {
            System.err.println("Error processing data queue: " + e.getMessage());
            context.processing = false;
        }
    }

    private void processSingleDataChunk(SelectionKey key, ClientContext context, byte[] currentData) throws IOException {
         // 将当前数据追加到临时缓冲区
        context.tempBuffer.write(currentData);
        // 检查临时缓冲区中是否包含结束标记
        String tempContent = context.tempBuffer.toString(StandardCharsets.UTF_8.name());
        int endIndex = tempContent.indexOf(context.EOF_MARK);

        byte[] fileData;
        if (endIndex != -1) {
            // 找到结束标记，只写入结束标记之前的数据
            fileData = tempContent.substring(0, endIndex).getBytes(StandardCharsets.UTF_8);
            context.fileOutputStream.write(fileData);
            context.receivedBytes += fileData.length;

            System.out.println("Received final chunk: " + fileData.length +
                    " bytes, Total: " + context.receivedBytes + " bytes");

            context.transmissionComplete = true;

            // 处理接收到的数据
            System.out.println("接收到完整信息，总共: " + context.receivedBytes + " bytes");

            // 清空临时缓冲区
            context.tempBuffer.reset();

            // 唤醒selector，以便及时处理OP_WRITE事件的变更
            selector.wakeup();
        } else {
            // 没有结束标记，将临时缓冲区的数据写入文件
            fileData = context.tempBuffer.toByteArray();

            // 没有结束标记，将所有数据写入文件
            context.fileOutputStream.write(fileData);
            context.receivedBytes += fileData.length;

            System.out.println("Received chunk: " + fileData.length +
                    " bytes, Total: " + context.receivedBytes + " bytes");

            // 清空临时缓冲区
            context.tempBuffer.reset();
        }
    }

    private void processFileName(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ClientContext context = (ClientContext) key.attachment();
        ByteBuffer buffer = context.buffer;
        byte[] fileData;
        byte[] currentData;
        // 累积数据直到找到文件名结束符
        currentData = new byte[buffer.remaining()];
        buffer.get(currentData);
        context.fileNameBuffer.write(currentData);

        // 检查是否收到文件名结束符（以\n结束）注意文件名不能超过buff长度
        String accumulatedData = context.fileNameBuffer.toString(StandardCharsets.UTF_8.name());
        int newlineIndex = accumulatedData.indexOf('\n');

        if (newlineIndex != -1) {
            // 提取文件名
            context.fileName = accumulatedData.substring(0, newlineIndex).trim();

            // 创建文件输出流
            try {
                Path filePath = Paths.get("/logs/received_files", context.fileName);
                Files.createDirectories(filePath.getParent());
                context.fileOutputStream = new FileOutputStream(filePath.toFile());
                context.fileNameReceived = true;
            } catch (Exception e){
                System.out.println("Error creating file: " + e.getMessage());
                // 清空读缓冲区
                buffer.clear();
                key.cancel();
                clientChannel.close();
                return;
            }


            // 处理文件名后的剩余数据（文件内容的第一部分）
            if (newlineIndex + 1 < accumulatedData.length()) {
                fileData = accumulatedData.substring(newlineIndex + 1).getBytes(StandardCharsets.UTF_8);
                // 将剩余数据暂存到临时缓冲区，以便后续处理
                context.tempBuffer.write(fileData);
                System.out.println("Stored initial chunk: " + fileData.length + " bytes");
            }

            System.out.println("Start receiving file: " + context.fileName);

            // 清空文件名缓冲区
            context.fileNameBuffer.reset();
        } else {
            // 没有收到文件名，回写数据直接结束
            // 清空读缓冲区
            buffer.clear();
        }
    }

    // 处理写事件
    private void handleWrite(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ClientContext context = (ClientContext) key.attachment();

        ByteBuffer writeBuffer = context.writeBuffer;

        try {
            // 将缓冲区内容写入通道
            clientChannel.write(writeBuffer);

            if (!writeBuffer.hasRemaining()) {
                // 数据已全部写完，清空缓冲区并重新关注 OP_READ
                writeBuffer.clear();
                key.interestOps(SelectionKey.OP_READ);

                // 如果传输已完成，可以考虑关闭连接
                if (context.transmissionComplete) {
                    closeConnection(key, clientChannel, context);
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to client: " + e.getMessage());
            closeConnection(key, clientChannel, context);
        }
    }

    // 处理客户端断开连接
    private void handleClientDisconnect(SelectionKey key, SocketChannel clientChannel, ClientContext context) {
        System.out.println("Client disconnected: " + getClientAddress(clientChannel));

        // 关闭资源
        closeConnection(key, clientChannel, context);
    }

    private void closeConnection(SelectionKey key, SocketChannel clientChannel, ClientContext context) {
        try {
            // 关闭文件输出流
            if (context.fileOutputStream != null) {
                context.fileOutputStream.close();
                System.out.println("File saved: " + context.fileName +
                        ", Size: " + context.receivedBytes + " bytes, FileStream closed");
                context.fileOutputStream = null;
            }

            // 取消SelectionKey
            if (key != null) {
                key.cancel();
            }

            // 关闭SocketChannel
            if (clientChannel != null && clientChannel.isOpen()) {
                clientChannel.close();
            }

            System.out.println("Connection closed for client: " + getClientAddress(clientChannel));
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    private String getClientAddress(SocketChannel clientChannel) {
        try {
            return clientChannel.getRemoteAddress().toString();
        } catch (IOException e) {
            return "Unknown";
        }
    }

    // 客户端上下文，用于保存每个客户端的缓冲区状态
    private static class ClientContext {
        final String EOF_MARK = "\n###END_OF_FILE###\n";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

        FileOutputStream fileOutputStream;  // 文件输出流
        String fileName;                    // 文件名
        boolean fileNameReceived = false;   // 是否已接收文件名
        long receivedBytes = 0;             // 已接收的字节数
        volatile boolean transmissionComplete = false; // 传输是否完成
        ByteArrayOutputStream fileNameBuffer = new ByteArrayOutputStream();
        ByteArrayOutputStream tempBuffer = new ByteArrayOutputStream();

        // 数据队列
        BlockingQueue<byte[]> dataQueue = new LinkedBlockingQueue<>();
        volatile boolean processing = false;
    }
}
