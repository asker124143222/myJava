package com.home.str;

/**
 * @author: xu.dm
 * @since: 2025/8/4 14:06
 **/
public class StringCompare {
    public static void main(String[] args) {
        String a = "abc";
        String b = "abc";
        System.out.println(a == b);

        String c = new String("abc");
        String d = new String("abc");
        System.out.println(a == c);
        System.out.println(c == d);
        System.out.println(c.equals(d));
        System.out.println(c.intern() == d.intern());
        System.out.println(c.intern() == a);

    }
}
