package com.home.errors;

/**
 * @author: xu.dm
 * @since: 2021/11/9 15:21
 * 如果没有加上break，也无匹配项，会执行所有选择项
 * 如果由匹配项，则会执行后续所有匹配项
 **/
public class SwitchError {
    public static void main(String[] args) {
        int x =0;
        switch (x) {
            default:
                System.out.println("default");
                // 如果default放到条件语句最前，那么它的break也很关键。
                // break;
            case 1:
                System.out.println(1);
            case 5:
                System.out.println(5);
            case 2:
            case 3:
                System.out.println(3);
            case 4:
                System.out.println(4);
        }
    }
}
