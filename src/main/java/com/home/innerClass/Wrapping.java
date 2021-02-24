package com.home.innerClass;

/**
 * @Author: xu.dm
 * @Date: 2021/2/23 11:22
 * @Version: 1.0
 * @Description: 简单包装，用于测试
 **/
public class Wrapping {
    private int i;

    static {
        System.out.println("Wrapping 静态块初始化...");
    }

    {
        System.out.println("Wrapping 实例初始化...");
    }
    public Wrapping(int x) {
        i = x;
        System.out.println("Wrapping 构造函数 ... 传入参数："+x);
    }

    public int value() {
        return i;
    }
}
