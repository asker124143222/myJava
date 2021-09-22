package com.home.thread;

/**
 * 三个线程顺序执行，通过volatile关键字保证自定义信号可见性
 * @author: xu.dm
 * @since: 2021/9/22 20:54
 */
public class SequentialCallDemo {
    private static volatile int signal = 1;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (signal == 1) {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("a:" + i);
                    }
                    signal = 2;
                    return;
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                if (signal == 2) {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("b:" + i);
                    }
                    signal = 3;
                    return;
                }
            }
        });

        Thread t3 = new Thread(() -> {
            while (true) {
                if (signal == 3) {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("c:" + i);
                    }
                    signal = 1;
                    return;
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
