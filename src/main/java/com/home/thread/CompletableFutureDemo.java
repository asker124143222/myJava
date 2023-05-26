package com.home.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author xu.dm
 * @since 2023/5/26 9:09
 **/
public class CompletableFutureDemo {
    public static void main(String[] args) {
        System.out.println("test start ...");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {

            try {
                TimeUnit.SECONDS.sleep(6);
                System.out.println("done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ok";
        });

        try {
            String result = future.get(5, TimeUnit.SECONDS);
            System.out.println("future返回："+result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("test end ...");
    }
}
