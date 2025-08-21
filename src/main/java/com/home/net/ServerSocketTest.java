package com.home.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: xu.dm
 * @since: 2025/8/18 16:08
 **/
public class ServerSocketTest {
    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port=" + port + ". Waiting for clients...");

            // 持续监听客户端连接
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     InputStream in = clientSocket.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                     OutputStream out = clientSocket.getOutputStream();
                     PrintWriter writer = new PrintWriter(out)) {

                    System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());

                    // 读取客户端消息
                    String message = reader.readLine();
                    if (message != null) {
                        System.out.println("Received message from client: " + message);

                        // 向客户端发送响应
                        writer.println("Hello Client");
                        writer.flush();
                    }

                    System.out.println("Client disconnected: " + clientSocket.getRemoteSocketAddress());
                } catch (IOException e) {
                    System.err.println("Error handling client: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
