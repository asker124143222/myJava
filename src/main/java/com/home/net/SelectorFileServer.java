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

/**
 * Socket NIO Channel selector
 * 完成文件传输
 * @author: xu.dm
 * @since: 2025/8/27 13:38
 **/
public class SelectorFileServer {
    private Selector selector;
    private ServerSocketChannel serverChannel;
    private final int port;

    public static void main(String[] args) {
        try {
            SelectorFileServer server = new SelectorFileServer(8080);
            server.start();
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public SelectorFileServer(int port) {
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
        int bytesRead = clientChannel.read(buffer);

        System.out.println("handleRead bytes read: " + bytesRead);
        if (bytesRead == -1 || context.transmissionComplete) {
            // 客户端关闭连接
            System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
            // 关闭文件输出流
            if (context.fileOutputStream != null) {
                context.fileOutputStream.close();
                System.out.println("File saved: " + context.fileName +
                        ", Size: " + context.receivedBytes + " bytes");
            }
            key.cancel();
            clientChannel.close();
            return;
        }

        byte[] currentData;
        byte[] fileData;
        if (bytesRead > 0) {
            // 切换 buffer 为读模式
            buffer.flip();

            // 如果尚未接收文件名
            if (!context.fileNameReceived) {
                // 累积数据直到找到文件名结束符
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                context.fileNameBuffer.write(data);

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
                    return;
                }
            } else {
                currentData = new byte[buffer.remaining()];
                buffer.get(currentData);

                // 将当前数据追加到临时缓冲区
                context.tempBuffer.write(currentData);
                // 检查临时缓冲区中是否包含结束标记
                String tempContent = context.tempBuffer.toString(StandardCharsets.UTF_8.name());
                int endIndex = tempContent.indexOf(context.EOF_MARK);

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

                    // 清空读缓冲区
                    buffer.clear();

                    // 准备发送确认响应
                    String response = "Received " + context.receivedBytes + " bytes\n";
                    ByteBuffer wrap = ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8));
                    context.writeBuffer.clear();
                    context.writeBuffer.put(wrap);
                    context.writeBuffer.flip();

                    // 修改关注事件为 OP_WRITE，准备写数据
                    key.interestOps(SelectionKey.OP_WRITE);

                    // 清空临时缓冲区
                    context.tempBuffer.reset();
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
            buffer.clear();
        }
    }

    // 处理写事件
    private void handleWrite(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ClientContext context = (ClientContext) key.attachment();

        ByteBuffer writeBuffer = context.writeBuffer;

        // 将缓冲区内容写入通道
        clientChannel.write(writeBuffer);

        if (!writeBuffer.hasRemaining()) {
            // 数据已全部写完，清空缓冲区并重新关注 OP_READ
            writeBuffer.clear();
            key.interestOps(SelectionKey.OP_READ);
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
        boolean transmissionComplete = false; // 传输是否完成
        ByteArrayOutputStream fileNameBuffer = new ByteArrayOutputStream();
        ByteArrayOutputStream tempBuffer = new ByteArrayOutputStream();
        byte[] remainingData = new byte[20]; // 用于保留每个分片最后20个字节的可能未完整的结束标志数据
    }
}
