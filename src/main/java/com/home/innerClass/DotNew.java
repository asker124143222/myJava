package com.home.innerClass;

/**
 * @Author: xu.dm
 * @Date: 2021/2/23 13:56
 * @Version: 1.0
 * @Description: 创建内部类对象，需要先创建外部类对象，然后通过外部类对象new 内部类对象
 **/
public class DotNew {
    private int code;
    private int getCode() { return code;}
    public class InnerClass{
        {
            // 访问外部类私有属性
            code = 1;
            System.out.println("InnerClass initial ...");
        }
        void getMessage() {
            // 可以调用外部类私有方法
            System.out.println("getMessage() : "+getCode());
        }
    }

    public static void main(String[] args) {
        DotNew dotNew = new DotNew();
        // 在拥有外部类对象之前是不可能创建内部类对象的。这是因为内部类对象会暗暗地连接到建它的外部类对象上。
        // 但是，如果你创建的是嵌套类（静态内部类），那么它就不需要对外部类对象的引用。
        InnerClass innerClass = dotNew.new InnerClass();
        innerClass.getMessage();
    }
}
