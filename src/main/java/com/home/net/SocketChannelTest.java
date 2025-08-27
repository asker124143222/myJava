package com.home.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author: xu.dm
 * @since: 2025/8/21 11:18
 **/
public class SocketChannelTest {
    public static void main(String[] args) throws Exception {
        // 创建SocketChannel并连接到服务器
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 8080));

        while (!socketChannel.finishConnect()) {
            Thread.sleep(100); // 等待连接建立
        }

        String filename = "E:\\myProgram\\java\\learning-master\\doc\\k8s\\k8s安装笔记3.md";
        File file = new File(filename);
        if(!file.exists()){
            throw new RuntimeException("文件不存在");
        }

        // 先发送文件名
        ByteBuffer buffer = ByteBuffer.wrap((file.getName()+"\n").getBytes(StandardCharsets.UTF_8));
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        try(FileInputStream fis = new FileInputStream(filename)){
            byte[] bytes = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(bytes)) != -1) {
                // 注意：最后一次读取可能不足1024字节
                System.out.println("从文件读取到 " + bytesRead + " 字节数据");
//                String data = new String(bytes, 0, bytesRead, StandardCharsets.UTF_8);
//                System.out.println("发送数据: " + data);
                buffer = ByteBuffer.wrap(bytes, 0, bytesRead);
                // 确保所有数据都被写入
                while (buffer.hasRemaining()) {
                    int written = socketChannel.write(buffer);
                    if (written == 0) {
                        Thread.sleep(10); // 如果没有写入任何数据，短暂等待
                    }
                }
            }
        }
        // 添加结束标记
        String endMarker = "\n###END_OF_FILE###\n";
        buffer = ByteBuffer.wrap(endMarker.getBytes(StandardCharsets.UTF_8));
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        System.out.println("===========本地数据发送完毕================");

        Thread.sleep(100);
        // 接收响应
        ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int bytesRead;
        while ((bytesRead = socketChannel.read(responseBuffer))>0) {
            System.out.println("从服务器读取：" + bytesRead + "字节");
            responseBuffer.flip();
            byte[] data = new byte[responseBuffer.remaining()];
            responseBuffer.get(data);
            outputStream.write(data);
            responseBuffer.clear();
        }

        // 只有当有数据时才处理
        if (outputStream.size() > 0) {
            String received = outputStream.toString();
            System.out.println("接收到服务器数据: " + received);
        }
        outputStream.close();
        socketChannel.close();
    }
}
