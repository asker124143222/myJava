package com.home.serializable;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @Author: xu.dm
 * @Date: 2021/5/18 21:28
 * @Description: 使用java自带的序列化
 * 类需要实现Serializable接口
 * 可以重写readObject和writeObject方法来自定义序列化的读写操作
 */
public class StudentSerializableTest2 {
    public static void main(String[] args) throws Exception {
        Student student = new Student();
        Address address = new Address("yn", "km", "gd");
        student.setName("张三");
        student.setAge(12);
        student.setAddress(address);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(out);
        outputStream.writeObject(student);

        byte[] buff = out.toByteArray();
        outputStream.close();

        ByteArrayInputStream input = new ByteArrayInputStream(buff);
        ObjectInputStream inputStream = new ObjectInputStream(input);
        Student student2 = (Student) inputStream.readObject();
//        student2.getAddress().setDetails("高大上");
        inputStream.close();
        System.out.println(student);
        System.out.println(student2);


    }
}
