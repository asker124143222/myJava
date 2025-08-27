package com.home.net;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author: xu.dm
 * @since: 2025/8/21 10:12
 **/
public class ServerSocketChannelTest {
    public static void main(String[] args) throws Exception {
        // 创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false); // 设置为非阻塞模式

        System.out.println("服务器启动，监听端口 8080...");

        while (true) {
            try (SocketChannel socketChannel = serverSocketChannel.accept();
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                if (socketChannel != null) {
                    System.out.println("客户端已连接" + socketChannel);

                    // 读取客户端数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    int bytesRead;
                    boolean transmissionComplete = false;
                    while (!transmissionComplete && (bytesRead = socketChannel.read(buffer)) > 0) {
                        System.out.println("接收到：" + bytesRead + "字节");
                        buffer.flip();
                        byte[] data = new byte[buffer.remaining()];
                        buffer.get(data);
                        outputStream.write(data);
                        buffer.clear();

                        // 检查是否收到完整传输结束标记
                        String currentContent = outputStream.toString();
                        if (currentContent.endsWith("###END_OF_FILE###\n")) {
                            transmissionComplete = true;

                            // 处理接收到的数据
                            System.out.println("接收到完整信息:\n" + currentContent);

                            // 发送响应
                            String response = "服务器已经收到信息";
                            ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
                            socketChannel.write(responseBuffer);
                            System.out.println("已回复客户端");
                        }
                    }

                }
            } catch (Exception e) {
                // 异常处理
                e.printStackTrace();
            }

            // 避免CPU占用过高
            Thread.sleep(1000);
        }
    }
}
