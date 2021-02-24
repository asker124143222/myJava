package com.home.innerClass;

/**
 * @Author: xu.dm
 * @Date: 2021/2/23 15:00
 * @Version: 1.0
 * @Description: 嵌套类
 * 如果不需要内部类对象与其外部类对象之间有联系，那么可以将内部类声明为 static，这通常称为嵌套类。
 * 想要理解 static 应用于内部类时的含义，就必须记住，普通的内部类对象隐式地保存了一个引用，指向创建它的外部类对象。
 * 然而，当内部类是 static 的时，就不是这样了。嵌套类意味着：
 *
 *     创建嵌套类的对象时，不需要其外部类的对象。
 *     不能从嵌套类的对象中访问非静态的外部类对象。
 *
 * 嵌套类与普通的内部类还有一个区别。普通内部类的字段与方法，只能放在类的外部层次上，
 * 所以普通的内部类不能有 static 数据和 static 字段，也不能包含嵌套类。
 **/
public class Parcel11 {
    private static class ParcelContents implements Contents {
        private int i = 11;
        @Override
        public int value() { return i; }
    }
    protected static final class ParcelDestination
            implements Destination {
        private String label;
        private ParcelDestination(String whereTo) {
            label = whereTo;
        }
        @Override
        public String readLabel() { return label; }
        // Nested classes can contain other static elements:
        public static void f() {}
        static int x = 10;
        static class AnotherLevel {
            public static void f() {}
            static int x = 10;
        }
    }
    public static Destination destination(String s) {
        return new ParcelDestination(s);
    }
    public static Contents contents() {
        return new ParcelContents();
    }
    public static void main(String[] args) {
        Contents c = contents();

        Destination d = destination("Tasmania");
    }
}
