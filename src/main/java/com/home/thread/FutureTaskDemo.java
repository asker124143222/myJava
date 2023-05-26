package com.home.thread;

import java.util.concurrent.*;

/**
 * @author xu.dm
 * @since 2023/5/26 11:45
 **/
public class FutureTaskDemo {
    public static void main(String[] args) {
        System.out.println("test start ...");
        FutureTask<String> futureTask = new FutureTask<String>(()->{
            TimeUnit.SECONDS.sleep(8);
            System.out.println("task done ...");
            return "task ok";
        });


        new Thread(futureTask).start();

        int seconds = 5;
        int count = 0;
        while (!futureTask.isDone()) {
            try {
                TimeUnit.SECONDS.sleep(1);
                if(++count > seconds){
                   break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(futureTask.isDone()){
            try {
                System.out.println(futureTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else{
            System.out.println("task is timeout ...");
        }

        System.out.println("test end ...");
    }
}
