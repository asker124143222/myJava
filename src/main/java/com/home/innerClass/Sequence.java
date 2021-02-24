package com.home.innerClass;

/**
 * @Author: xu.dm
 * @Date: 2021/2/24 9:43
 * @Version: 1.0
 * @Description: 一个固定大小的容器序列，可存储任何对象，提供迭代接口
 *
 * 为什么不使用Sequence类实现Selector接口呢？
 * 是因为使用内部类来实现Selector有更好的扩展性，
 * 比如：目前我们实现的迭代器是正向迭代，如果需要再添加一个反向迭代器，只需再新增一个内部类就可以实现，不会对外部类产生不良影响。
 * 如果是在外部类Sequence上实现接口必然会过多影响外部类对外暴露的方法，比如next()接口方法到底是正向迭代还是反向迭代的方法？
 * 另外，如果要实现是多个抽象类，java只支持单继承，但是使用内部类就可以实现对多重继承的补充。
 **/
public class Sequence {
    private Object[] items;
    private int next = 0;

    public Sequence(int size) {
        items = new Object[size];
    }

    public void add(Object item) {
        if(next < items.length) {
            items[next++] = item;
        }
    }

    private class SequenceSelector implements Selector {
        private int i = 0;
        public boolean end() {
            return i == items.length;
        }

        public Object current() {
            return items[i];
        }

        public void next() {
            if(i<items.length) {
                i ++ ;
            }
        }
    }

    public Selector selector() {
        return new SequenceSelector();
    }

    public static void main(String[] args) {
        Sequence sequence = new Sequence(10);
        for (int i = 0; i < 10; i++) {
            sequence.add(String.valueOf(i));
        }

        Selector selector = sequence.selector();
        while (!selector.end()){
            System.out.println(selector.current());
            selector.next();
        }
    }

}
