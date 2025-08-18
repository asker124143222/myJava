package com.home.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author: xu.dm
 * @since: 2025/8/18 16:50
 **/
public class UrlTest {
    public static void main(String[] args) {
        String urlString = "http://172.100.220.113:8888";
        try {
            // 创建URL对象
            URL url = new URL(urlString);

            // 打开连接
            URLConnection connection = url.openConnection();

            // 获取输入流读取内容
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
