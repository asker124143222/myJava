package com.home.time;

import java.time.LocalDate;

/**
 * @Author: xu.dm
 * @Date: 2021/1/4 9:12
 * @Version: 1.0
 * @Description: TODO
 **/
public class DateTestMain {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now().minusMonths(1);
        System.out.println(localDate);
    }
}
