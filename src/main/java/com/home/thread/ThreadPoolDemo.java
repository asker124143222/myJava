package com.home.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xu.dm
 * @Date: 2021/7/11 9:33
 * @Description:
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                5,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(60), new ThreadPoolExecutor.CallerRunsPolicy());


        for (int i = 0; i < 30; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "::" + finalI + ":: 正在处理...");
            });

        }

        threadPool.shutdown();
    }
}
