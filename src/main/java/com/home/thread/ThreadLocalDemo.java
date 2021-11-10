package com.home.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author: xu.dm
 * @since: 2021/11/4 10:53
 **/
public class ThreadLocalDemo {
    public static void main(String[] args) throws InterruptedException {
        firstStack();
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        Thread thread = Thread.currentThread();
        // 在这里打断点，观察thread对象里的ThreadLocalMap数据
        System.out.println(thread);

    }

    /**
     *  假设ThreadLocalMap的key被设计成强引用，这里调用完成后，对象a里的local不会被回收，
     *  造成两个后果：变量逃逸和local对象无法回收，造成内存泄漏（直到引用他的线程结束）
     */
    private static void firstStack(){
        TestMyClass myObject = new TestMyClass();
        System.out.println("value: "+ myObject.get());
    }
    private static class TestMyClass {
        private ThreadLocal<String> local = ThreadLocal.withInitial(() -> "in class A");

        public String get(){
            return local.get();
        }
        public void set(String str){
            local.set(str);
        }
    }
}
