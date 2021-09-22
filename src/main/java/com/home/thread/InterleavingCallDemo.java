package com.home.thread;

import java.util.concurrent.Semaphore;

/**
 * 三个线程交错执行，使用Semaphore信号量方式限制线程
 *
 * @author: xu.dm
 * @since: 2021/9/22 20:37
 */
public class InterleavingCallDemo {
    private static Semaphore s1 = new Semaphore(1);
    private static Semaphore s2 = new Semaphore(1);
    private static Semaphore s3 = new Semaphore(1);

    public static void main(String[] args) {
        try {
            // 先占用2个信号
            s2.acquire();
            s3.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            while (true) {
                try {
                    s1.acquire();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A");
                // 释放线程二的信号
                s2.release();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    s2.acquire();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B");
                // 释放线程三的信号
                s3.release();
            }
        }).start();


        new Thread(() -> {
            while (true) {
                try {
                    s3.acquire();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("C");
                // 释放线程一的信号
                s1.release();
            }
        }).start();
    }

}
