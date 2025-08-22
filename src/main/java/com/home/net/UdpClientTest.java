package com.home.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @author: xu.dm
 * @since: 2025/8/22 15:44
 **/
public class UdpClientTest {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 9876;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
            Scanner scanner = new Scanner(System.in);

            System.out.println("UDP客户端启动，连接服务器: " + SERVER_ADDRESS + ":" + SERVER_PORT);

            while (true) {
                System.out.print("请输入要发送的消息 (输入'quit'退出): ");
                String message = scanner.nextLine();

                if ("quit".equals(message)) {
                    break;
                }

                // 发送数据到服务器
                byte[] sendData = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
                clientSocket.send(sendPacket);

                // 接收服务器回复
                byte[] receiveBuffer = new byte[BUFFER_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket);

                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("服务器回复: " + response);
            }

            System.out.println("客户端退出");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
