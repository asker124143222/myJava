package com.home.date;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @Author: xu.dm
 * @Date: 2021/3/8 9:14
 * @Version: 1.0
 * @Description: Date 到 LocalDate转换
 * 原理就是通过Instant这个类来做转换
 **/
public class DateToLocalDate {
    public static void main(String[] args) {
        Date currentDate = new Date();
        LocalDate endDate = LocalDate.of(2021, 1, 1);
        LocalDate startDate = LocalDate.of(2020, 2, 1);
        LocalDate nowDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long between = ChronoUnit.MONTHS.between(startDate, endDate);
        long between2 = ChronoUnit.MONTHS.between(startDate, nowDate);
        System.out.println(between);
        System.out.println(between2);

        Date date = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println(date);
    }
}
