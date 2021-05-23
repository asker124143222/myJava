package com.home.design.builder2;

/**
 * @Author: xu.dm
 * @Date: 2021/5/23 11:11
 * @Description:
 */
public class Client {
    public static void main(String[] args) {
        Director director = new Director(new MobikeBuilder());
        Bike b = director.construct();
        System.out.println(b);


        director = new Director(new OfoBuilder());
        b = director.construct();
        System.out.println(b);
    }
}
