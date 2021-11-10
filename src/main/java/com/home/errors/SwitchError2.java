package com.home.errors;

/**
 * @author: xu.dm
 * @since: 2021/11/9 15:35
 * 注意:default后没有break,所以会一直执行到有break的分支，即case 2
 **/
public class SwitchError2 {
    public static void main(String[] args) {
        int num = 0;
        switch (num) {
            default:
                System.out.println("Default");
                num++;
            case 1:
                System.out.println("num = " +num);
                num += 2;
            case 2:
                System.out.println("num = " + ++num);
                break;
            case 3:
                System.out.println("num = " +num);
                break;
        }
    }
}
