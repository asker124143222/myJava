package com.home.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author xu.dm
 * @since 2022/3/28 10:39
 * 延时队列
 **/
public class DelayQueueTest {
    public static void main(String[] args) throws InterruptedException {
        final DelayQueue<DelayedEvent> delayQueue = new DelayQueue<>();
        final long timeFirst = System.currentTimeMillis() + 10000;
        delayQueue.offer(new DelayedEvent(timeFirst, "1"));
        System.out.println("Done");
        System.out.println(delayQueue.take().msg);
        System.out.println("Done2");
    }

    static class DelayedEvent implements Delayed {
        private long startTime;
        private String msg;

        public DelayedEvent(long startTime, String msg) {
            this.startTime = startTime;
            this.msg = msg;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = startTime - System.currentTimeMillis();
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.startTime - ((DelayedEvent) o).startTime);
        }
    }


}
