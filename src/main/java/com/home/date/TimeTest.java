package com.home.date;

/**
 * @author: xu.dm
 * @since: 2021/8/3 11:26
 **/
public class TimeTest {
    public static void main(String[] args) {
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        String password = "abcdefg162796120433200000";
        String withoutNoise = password.substring(0,password.length()-5);
        System.out.println(withoutNoise);
        String result0 = withoutNoise.substring(0,withoutNoise.length()-13);
        String result1 = withoutNoise.substring(withoutNoise.length()-13);

        System.out.println(result0);
        System.out.println(result1);
    }
}
