package com.home.thread;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: xu.dm
 * @since: 2021/8/3 9:40
 **/
public class WaitAndNotify {
    public static void main(String[] args) {
        //（3）wait()方法必须在多线程共享的对象上调用
        // 这个队列就是给消费者、生产者两个线程共享的对象
        Queue<Integer> queue = new LinkedList<>();
        int maxSize = 5;
        Producer p = new Producer(queue, maxSize);
        Consumer c = new Consumer(queue, maxSize);
        Thread pT = new Thread(p);
        Thread pC = new Thread(c);
        // 生产者线程启动，获取锁
        pT.start();
        // 消费者线程启动
        pC.start();
    }
}

/**
 * 生产者，有详细的注释
 */
class Producer implements Runnable{
    private Queue<Integer> queue;
    private int maxSize;
    public Producer(Queue<Integer> queue, int maxSize){
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        // 这里为了方便演示做了一个死循环，现实开发中不要这样搞
        while (true){
            //（1）wait()、notify()和notifyAll()必须在synchronized修饰的方法或代码块中使用
            synchronized (queue){
                //（2）在while循环里而不是if语句下使用wait()，确保在线程睡眠前后都检查wait()触发的条件（防止虚假唤醒）
                while (queue.size() == maxSize){
                    try{
                        System.out.println("Queue is Full");
                        // 生产者线程进入等待状态，在此对象监视器上等待的所有线程（其实只有那个消费者线程）开始争夺锁
                        queue.wait();
                    }catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                }
                SecureRandom random = new SecureRandom();
                int i = random.nextInt();
                System.out.println("Produce " + i);
                queue.add(i);
                // 唤醒这个Queue对象的等待池中的所有线程（其实只有那个消费者线程），等待获取对象监视器
                queue.notifyAll();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 消费者
 */
class Consumer implements Runnable{
    private Queue<Integer> queue;
    private int maxSize;
    public Consumer(Queue<Integer> queue, int maxSize){
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true){
            synchronized (queue){
                while (queue.isEmpty()){
                    System.out.println("Queue is Empty");
                    try{
                        queue.wait();
                    }catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                }
                int v = queue.remove();
                System.out.println("Consume " + v);
                queue.notifyAll();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
