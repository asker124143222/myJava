package com.home.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xu.dm
 * @since 2022/3/28 11:18
 **/
public class TimeFormatterTest {
    public static void main(String[] args) {
        String time1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        System.out.println(time1);
    }
}
