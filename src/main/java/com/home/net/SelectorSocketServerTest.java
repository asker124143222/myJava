package com.home.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: xu.dm
 * @since: 2025/8/25 14:05
 **/
public class SelectorSocketServerTest {
    private Selector selector;
    private ServerSocketChannel serverChannel;
    private final int port;

    public static void main(String[] args) {
        try {
            SelectorSocketServerTest server = new SelectorSocketServerTest(8080);
            server.start();
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public SelectorSocketServerTest(int port) {
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

        System.out.println("Echo Server started on port " + port);

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

        if (bytesRead == -1) {
            // 客户端关闭连接
            System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
            key.cancel();
            clientChannel.close();
            return;
        }

        if (bytesRead > 0) {
            // 切换 buffer 为读模式
            buffer.flip();

            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);

            String received = new String(data);
            System.out.println("Received from client: " + received);
            // 清空读缓冲区
            buffer.clear();

            String response = "服务器回复如下:\n " + received;
            ByteBuffer wrap = ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8));
            // 清空写缓冲区，确保有足够空间
            context.writeBuffer.clear();
            // 将数据复制到写缓冲区，准备回显
            context.writeBuffer.put(wrap);
            context.writeBuffer.flip();
            // 修改关注事件为 OP_WRITE，准备写数据
            key.interestOps(SelectionKey.OP_WRITE);
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
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
    }
}