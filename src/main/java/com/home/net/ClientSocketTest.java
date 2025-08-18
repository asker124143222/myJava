package com.home.net;

import java.io.*;
import java.net.Socket;

/**
 * @author: xu.dm
 * @since: 2025/8/18 16:06
 **/
public class ClientSocketTest {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // 网络操作
            out.println("GET / HTTP/1.1");
            out.println("Host: example.com");
            out.println(); // 空行表示请求头结束

            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                System.out.println(responseLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
