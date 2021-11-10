package com.home.thread;

/**
 * @author: xu.dm
 * @since: 2021/11/9 11:16
 * InterruptedTask大部分时间都是阻塞在sleep(100)上，当其他线程通过调用执行线程的interrupt()方法来中断执行线程时，
 * 大概率的会触发InterruptedException异常，在触发InterruptedException异常的同时，
 * JVM会同时把线程的中断标志位清除，
 * 所以，这个时候在run()方法中判断的currentThread.isInterrupted()会返回false，也就不会退出当前while循环了。
 **/
public class InterrupterThreadDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(new InterruptedTask());
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

    static class InterruptedTask implements Runnable {
        @Override
        public void run() {
            Thread currentThread = Thread.currentThread();
            while (true) {
                if(currentThread.isInterrupted()) {
                    System.out.println(currentThread.getName() + "线程收到中断信号，执行break ... ");
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 必须加上这句，以防止外部触发interrupt的时候，线程刚好在休眠，这个时候会触发InterruptedException异常，
                    // 而JVM在触发此异常的收会清除线程interrupt的状态，所以要手动重新设置interrupt的状态，才能退出前面的while循环。
                    currentThread.interrupt();
                }
            }
        }
    }
}
