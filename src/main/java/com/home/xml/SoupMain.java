package com.home.xml;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @Author: xu.dm
 * @Date: 2021/1/29 20:30
 * @Description:
 */
public class SoupMain {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = SoupMain.class.getResourceAsStream("/config/ehcache.xml");
        Document document = Jsoup.parse(inputStream, "utf-8", "");
        IOUtils.close(inputStream);
        System.out.println(document);
        System.out.println("###################################");
        Elements elements = document.getElementsByTag("cache");
        System.out.println(elements);

        System.out.println("###################################");
        Elements elements1 = document.getElementsByAttributeValue("name", "userCache");
        System.out.println(elements1);

        System.out.println("###################################");
        Elements defaultcache = document.select("defaultcache");
        System.out.println(defaultcache);

        System.out.println("###################################");
        Elements elements2 = document.select("cache[name='dicCache']");
        System.out.println(elements2);

        System.out.println();
//        Document doc2 = Jsoup.connect("http://www.baidu.com").get();
//        System.out.println(doc2);
    }
}
