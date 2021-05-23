package com.home.design.builder;

/**
 * @Author: xu.dm
 * @Date: 2021/5/23 10:35
 * @Description:
 */
public class Client {
    public static void main(String[] args) {
        Phone phone = new Phone.Builder()
                .cpu("intel")
                .mainBoard("华硕")
                .memory("8g")
                .screen("三星")
                .build();

        System.out.println(phone);
    }
}
