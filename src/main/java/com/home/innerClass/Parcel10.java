package com.home.innerClass;

/**
 * @Author: xu.dm
 * @Date: 2021/2/23 11:06
 * @Version: 1.0
 * @Description: 内部类初始化
 * 在匿名类中不可能有命名构造器（因为它根本没名字！），但通过实例初始化，就能够达到为匿名内部类创建一个构造器的效果
 **/
public class Parcel10 {
    static {
        System.out.println("匿名类初始化测试... ...");
    }

    /**
     * 如果在定义一个匿名内部类时，它要使用一个外部环境（在本匿名内部类之外定义）对象，
     * 那么编译器会要求其（该对象）参数引用是 final 或者是 “effectively final”
     * （也就是说，该参数在初始化后不能被重新赋值，所以可以当作 final）的，
     * 就像你在 destination() 的参数中看到的那样。这里省略掉 final 也没问题，
     * 但通常加上 final 作为提醒比较好。
     * 即使不加 final, Java 8 的编译器也会为我们自动加上 final，以保证数据的一致性。
     */
    public Destination destination(final String dest,final double price) {
        return new Destination() {
            private int cost;
            // 初始化实例
            {
                cost = (int) Math.round(price);
                if(cost > 100) {
                    System.out.println("超出预算:"+cost);
                }
            }
            private String label = dest;
            @Override
            public String readLabel() {
                return "destination: "+label+", price: "+cost;
            }
        };
    }

    public static void main(String[] args) {
        Parcel10 parcel10 = new Parcel10();
        Destination destination = parcel10.destination("昆明", 600.65);
        System.out.println(destination.readLabel());

    }
}
