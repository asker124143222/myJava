package com.home.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author: xu.dm
 * @since: 2025/8/25 14:24
 **/
public class SelectorSocketClientTest {
    private SocketChannel clientChannel;
    private final String host;
    private final int port;

    public SelectorSocketClientTest(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        clientChannel = SocketChannel.open();
        clientChannel.connect(new InetSocketAddress(host, port));
        System.out.println("Connected to server");
    }

    public void sendMessage(String message) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        clientChannel.write(buffer);

        // 读取服务器响应
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int bytesRead = clientChannel.read(readBuffer);

        if (bytesRead > 0) {
            readBuffer.flip();
            byte[] response = new byte[bytesRead];
            readBuffer.get(response);
            System.out.println("Server response: " + new String(response));
            readBuffer.clear();
        }
    }

    public void disconnect() throws IOException {
        if (clientChannel != null && clientChannel.isOpen()) {
            clientChannel.close();
        }
    }

    public static void main(String[] args) {
        try {
            SelectorSocketClientTest client = new SelectorSocketClientTest("localhost", 8080);
            client.connect();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter messages (type 'quit' to exit):");

            String input;
            while (!(input = scanner.nextLine()).equals("quit")) {
                client.sendMessage(input);
            }

            client.disconnect();
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
