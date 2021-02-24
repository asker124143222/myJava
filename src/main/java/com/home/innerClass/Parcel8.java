package com.home.innerClass;

/**
 * @Author: xu.dm
 * @Date: 2021/2/23 11:23
 * @Version: 1.0
 * @Description: 内部类向上转型或者重写
 * 当将内部类向上转型为其基类，尤其是转型为一个接口的时候，内部类就有了用武之地。
 * （从实现了某个接口的对象，得到对此接口的引用，与向上转型为这个对象的基类，实质上效果是一样的。）
 * 这是因为此内部类-某个接口的实现-能够完全不可见，并且不可用。
 * 所得到的只是指向基类或接口的引用，所以能够很方便地隐藏实现细节。
 **/
public class Parcel8 {
    private void outOuterMessage() {
        System.out.println("Parcel8 out ... ");
    }
    public Wrapping wrapping(int x) {
        // 尽管 Wrapping 只是一个具有具体实现的普通类，但它还是被导出类当作公共“接口”来使用。
        // 实际上这是内部匿名类，它扩展了Wrapping类
        // 通常情况，使用内部匿名类我们应该返回接口，以保证足够的扩展。
        return new Wrapping(x) {
            @Override
            public int value() {
                // 通过外部类.this可以获取外部类的实例
                System.out.println(Parcel8.this);
                return super.value() * 47;
            }
        };
    }

    public static void main(String[] args) {
        Parcel8 p = new Parcel8();
        Wrapping w = p.wrapping(10);
        System.out.println(p);
        System.out.println("value: "+w.value());
    }
}
