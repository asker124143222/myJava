package com.home.util;

/**
 * @author: xu.dm
 * @since: 2025/8/1 13:57
 **/
public class RuntimeTest {
    public static void main(String[] args) {
        // 获取当前系统有效processors数量
        long availableProcessors = Runtime.getRuntime().availableProcessors();

        // 获取当前系统可用内存（字节）
        long freeMemory = Runtime.getRuntime().freeMemory();

        // 获取JVM内存信息（字节）
        long totalMemory = Runtime.getRuntime().totalMemory();

        // 获取JVM最大可用内存（字节）
        long maxMemory = Runtime.getRuntime().maxMemory();

        System.out.println("可用cpu：" + availableProcessors);
        System.out.println("可用内存：" + freeMemory / 1024 / 1024);
        System.out.println("JVM总内存：" + totalMemory / 1024 / 1024);
        System.out.println("JVM最大可用内存：" + maxMemory / 1024 / 1024);

    }
}
