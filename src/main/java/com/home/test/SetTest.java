package com.home.test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Author: xu.dm
 * @Date: 2021/3/23 10:53
 * @Version: 1.0
 * @Description: TODO
 **/
public class SetTest {
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        Set<Integer> set1 = new TreeSet<>(Comparator.reverseOrder());

        for (int i = 30; i > 1 ; i--) {
            set.add(i);
            set1.add(i);
        }

        System.out.println(set.toString());
        System.out.println(set1.toString());
    }
}
