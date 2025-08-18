package com.home.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author: xu.dm
 * @since: 2021/11/9 13:41
 **/
public class MyTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "\\xC3\\xA4\\xC2\\xBA\\xC2\\x91D60029";
        String str2 = str.replaceAll("\\\\x", "%");
        System.out.println(str2);

        System.out.println(URLDecoder.decode(str2, "gbk"));

        String str3 = "云";
        String str4 = new String(str3.getBytes(), "GBK");
        System.out.println(str4);

        Double currentRate = Math.round((945.21 / 1000.22)*100) * 0.01d;
        System.out.println(currentRate);

        System.out.println(String.format("%.2f", 3.1465926001));
    }


}
