package com.home.spi;

import java.util.ServiceLoader;

/**
 * @author: xu.dm
 * @since: 2021/9/3 11:45
 **/
public class SpiTest {
    public static void main(String[] args) {
        ServiceLoader<Fruit> fruits = ServiceLoader.load(Fruit.class);

        for (Fruit fruit : fruits) {
            System.out.println(fruit.getName());
        }
    }
}
