package com.home.basic;

/**
 * @author: xu.dm
 * @since: 2021/11/9 15:59
 * 需明白局部变量表和操作数栈的原理才能正确理解
 **/
public class AutoIncrement {
    public static void main(String[] args) {
        int i = 1;
        // i先入操作数栈，i++这个自增操作是在局部变量表里完成，所以压入操作数栈的i没有变化，并且被重新赋值给i，所以i的变化是->2->1
        i = i ++;
        // i++在局部变量表里完成自增，所以局部变量表里的i=2，操作数栈里的i=1，然后赋值给j，所以j=1
        int j = i++;
        // 第一步：i在上面的步骤中自增，所以i=2，压入操作数栈为2，
        // 第二步：++i操作，先在本地变量表里自增，那么i=3，然后压入操作数栈为3
        // 第三步：i++操作，这个时候i=3，把i压入操作数栈为3，然后自增，i=4
        // 第四步：这个时候压栈数据完成,操作数栈有三个值，从栈底到栈顶为2,3,3()，计算++i和i++，即3*3=9，把9压回操作数栈（因为还有一个加法运算没完成）
        // 第五步：计算i（栈里） + 刚才的数(9)=11，把结果赋值给k，所以k=11，局部变量表里的i=4，在出入栈的过程中没有对它赋值。
        int k = i + ++i * i++;
        System.out.println("i="+i);
        System.out.println("j="+j);
        System.out.println("k="+k);
    }
}
