package com.home.test;

/**
 * @Author: xu.dm
 * @Date: 2021/1/30 15:39
 * @Description: 在调整线程栈大小后，countInteger可以正确返回，线程栈jdk8默认参数-Xss1m
 * 因为传入的short值（或者是int值），是从负数到正数，比如short是-2^15到2^15-1，当num参数累计到该类型最大后，就会溢出，从负数重新开始计算。
 */
public class IntegerTest {
    private static int count = 0;
    public static void main(String[] args) {
        countInteger((short) 1);
    }

    public static void countInteger(short num) {
        count++;
        System.out.println("stack depth: " + count);
        if(num  == 0){
            System.out.println("num 等于 0 ");
            return;
        }
        countInteger(++num);
    }
}
