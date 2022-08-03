package com.home.file;

import java.io.*;
import java.net.URLDecoder;

/**
 * @author xu.dm
 * @since 2022/8/3 11:36
 **/
public class ResourceTest {

    public static void main(String[] args) throws IOException {
        getResourceTest4();
    }


    private static void getResourceTest(){
        String path = ResourceTest.class.getClassLoader().getResource("").getPath();
        System.out.println(path);

        String filename = "META-INF/services/com.home.spi.Fruit";
        String filePath = path + filename;
        try {
            getFileContent(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getResourceTest2() throws IOException {
        String filename2 = "config/中文配置.properties";
        String path = ResourceTest.class.getClassLoader().getResource(filename2).getPath();
        System.out.println(path);

        String filePath = URLDecoder.decode(path, "UTF-8");
        System.out.println(filePath);

        getFileContent(filePath);
    }

    private static void getResourceTest3() throws IOException {
        //直接通过文件名+getFile()来获取文件。如果是文件路径的话getFile和getPath效果是一样的，
        // 如果是URL路径的话getPath是带有参数的路径。如下所示：
        // url.getFile()=/pub/files/foobar.txt?id=123456
        // url.getPath()=/pub/files/foobar.txt
        // 暂时没法测试
        String filename = "config/中文配置.properties?id=123";
        String path = ResourceTest.class.getClassLoader().getResource("").getFile();
        System.out.println(path);

        String filePath = URLDecoder.decode(path, "UTF-8");
        System.out.println(filePath);

        getFileContent(filePath);
    }

    private static void getResourceTest4() throws IOException {
        // 直接使用getResourceAsStream方法获取流，上面的几种方式都需要获取文件路径，
        // 但是在SpringBoot中所有文件都在jar包中，没有一个实际的路径，因此可以使用以下方式。
        String filename = "config/中文配置.properties";
        InputStream in = ResourceTest.class.getClassLoader().getResourceAsStream(filename);
        getFileContent(in);
    }

    private static void getResourceTest5() throws IOException {
        String filename = "config/中文配置.properties";

    }


    public static void getFileContent(Object fileInPath) throws IOException {
        BufferedReader br = null;
        if (fileInPath == null) {
            return;
        }
        if (fileInPath instanceof String) {
            br = new BufferedReader(new FileReader(new File((String) fileInPath)));
        } else if (fileInPath instanceof InputStream) {
            br = new BufferedReader(new InputStreamReader((InputStream) fileInPath));
        }
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }
}
