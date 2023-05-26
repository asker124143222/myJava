package com.home.test;

import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: xu.dm
 * @since: 2021/11/9 13:41
 **/
public class MyTest {
    public static void main(String[] args) {
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);

        AtomicInteger count = new AtomicInteger(100);
        int c = -1;
        c = count.getAndDecrement();
        System.out.println(count.get());
        System.out.println(c);

        System.out.println(" =============== ");
    }


    private void test() {
        LinkedHashMap<Object, Object> map = new LinkedHashMap<>();

        String uuu = UUID.randomUUID().toString();
        System.out.println(uuu);

        map.put(uuu, uuu.hashCode());



    }


}
