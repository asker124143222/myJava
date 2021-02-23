package com.home.test;

/**
 * @Author: xu.dm
 * @Date: 2021/2/23 18:55
 * @Description: Integer -128到127之间的静态缓存
 * valueOf会将常用的值（-128 to 127）cache起来。当i值在这个范围时，会比用构造方法Integer(int)效率和空间上更好
 * 仅限于Integer.valueOf()
 *
 * public static Integer valueOf(int i) {
 *         if(i >= -128 && i <= IntegerCache.high)
 *             return IntegerCache.cache[i + 128];
 *         else
 *             return new Integer(i);
 *     }
 */
public class IntegerCache {
    public static void main(String[] args) {
        Integer integer1 = new Integer(1);
        Integer integer2 = new Integer(1);
        System.out.println(integer1 == integer2);
        Integer i3 = Integer.valueOf(1);
        Integer i4 = Integer.valueOf(1);
        System.out.println(i3 == i4);
        System.out.println(Integer.valueOf(1) == Integer.valueOf(1));
        System.out.println(Integer.valueOf(128) == Integer.valueOf(128));
    }
}
