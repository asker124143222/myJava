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
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket clientSocket = serverSocket.accept();
             InputStream in = clientSocket.getInputStream();

             BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             OutputStream out = clientSocket.getOutputStream();
             PrintWriter writer = new PrintWriter(out)) {

            System.out.println("Server started port=" + port + " Waiting for client...");
            System.out.println("Client connected" + clientSocket);

            // 读取客户端消息
            String message = reader.readLine();
            System.out.println("Received message from client: " + message);

            // 向客户端发送响应
            writer.write("Hello Client\n");
            writer.flush();

            System.out.println("Client disconnected");
            System.out.println("Server closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
