package com.home.thread;

import java.util.concurrent.CountDownLatch;

/**
 * 三个线程同时执行，使用CountDownLatch的await让线程先等待，等到计数器为0时，各线程同时执行。
 *
 * @author: xu.dm
 * @since: 2021/9/22 20:10
 */
public class MeantimeCallDemo {
    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public static void main(String[] args) throws InterruptedException {
        int size = 3;

        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < size; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    System.out.println(System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(500);
        countDownLatch.countDown();
    }
}
