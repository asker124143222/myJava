package com.home.codec;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @Author: xu.dm
 * @Date: 2021/1/12 10:18
 * @Version: 1.0
 * @Description: URL编码测试
 **/
public class UrlCodec {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "http://wwww.baidu.com/?name=zhangsan&age=18&email=zhang996@126.com&location=工作996，生病ICU";
        String encode = URLEncoder.encode(s, "UTF-8");
        System.out.println(encode);

        String decode = URLDecoder.decode(encode, "utf-8");
        System.out.println(decode);


        String s2 = "云D60029";
        String encode2 = URLEncoder.encode(s2, "UTF-8");
        System.out.println(encode2);

        String decode2 = URLDecoder.decode(encode2, "utf-8");
        System.out.println(decode2);

    }
}
