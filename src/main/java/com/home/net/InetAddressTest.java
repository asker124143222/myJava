package com.home.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: xu.dm
 * @since: 2025/8/18 16:00
 **/
public class InetAddressTest {
    public static void main(String[] args) {
        try {
            // 获取本机InetAddress对象
            InetAddress localAddress = InetAddress.getLocalHost();
            System.out.println("Local Address: " + localAddress);
            // 根据主机名获取InetAddress对象
            InetAddress hostAddress = InetAddress.getByName("www.baidu.com");
            System.out.println("Host Address: " + hostAddress);

            // 根据IP地址获取InetAddress对象
            InetAddress ipaddress = InetAddress.getByName("192.168.44.1");
            System.out.println("IP Address: " + ipaddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
