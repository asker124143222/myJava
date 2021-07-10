package com.home.thread;

import java.util.concurrent.TimeUnit;

/**
 * @Author: xu.dm
 * @Date: 2021/7/10 13:06
 * @Description: 死锁示例，使用jstack pid查看
 */
public class DealLockDemo {
    static Object a = new Object();
    static Object b = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (a) {
                System.out.println(Thread.currentThread().getName()+":: 锁住a，准备获取b");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    System.out.println(Thread.currentThread().getName()+":: 锁住a, b");
                }
            }
        },"aa").start();


        new Thread(()->{
            synchronized (b) {
                System.out.println(Thread.currentThread().getName()+":: 锁住b，准备获取a");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {
                    System.out.println(Thread.currentThread().getName()+":: 锁住a, b");
                }
            }
        },"bb").start();
    }
}
