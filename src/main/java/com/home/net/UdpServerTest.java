package com.home.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author: xu.dm
 * @since: 2025/8/22 15:44
 **/
public class UdpServerTest {
    private static final int PORT = 9876;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            System.out.println("UDP服务器启动，监听端口: " + PORT);

            byte[] receiveBuffer = new byte[BUFFER_SIZE];

            while (true) {
                // 创建接收数据包
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                // 接收数据
                serverSocket.receive(receivePacket);

                // 获取客户端信息
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

                System.out.println("收到来自 " + clientAddress.getHostAddress() + ":" + clientPort + " 的消息: " + message);

                // 回复客户端
                String response = "服务器收到: " + message;
                byte[] sendData = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
