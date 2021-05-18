package com.home.serializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: xu.dm
 * @Date: 2021/5/18 20:14
 * @Description:
 */
public class Student implements Serializable {
    private String name;
    private int age;
    private Address address;

    public Student() {
    }

    public Student(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        System.out.println("writeObject: "+this);
        // 方式1： 缺省写对象方式，自动将整个对象写入，out还可以再写入其他自定义内容
//        out.defaultWriteObject();

        // 方式2： 自定义每个字段读写
        out.writeObject(this.name);
        out.writeInt(this.age);
        out.writeObject(this.address);

        Date date = new Date();
        out.writeObject(date);

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // 方式1： 缺省写对象方式
//        in.defaultReadObject();

        // 方式2： 自定义每个字段读写
        this.name = (String) in.readObject();
        this.age = in.readInt();
        this.address = (Address) in.readObject();

        Date date = (Date)in.readObject();
        Date now = new Date();
        long offset = now.getTime() - date.getTime();

        if(offset<100){
            System.out.println("在正常的时间内接收到序列化对象offset:"+offset);
        }else{
            System.out.println("接收传输时间过长offset:"+offset);
        }

        System.out.println("readObject: "+this);
    }
}
