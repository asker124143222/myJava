package com.home.date;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @Author: xu.dm
 * @Date: 2021/3/28 16:24
 * @Description: 时间之差计算
 */
public class DateCalc {
    public static void main(String[] args) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(30);

        // 第一种方式
        long between = ChronoUnit.MINUTES.between(startTime, endTime);
        System.out.println(between);

        // 第二种方式
        Duration duration = Duration.between(startTime, endTime);
        long result = duration.toMinutes();

        System.out.println(result);
    }
}
