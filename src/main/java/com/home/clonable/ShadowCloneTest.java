package com.home.clonable;

/**
 * @Author: xu.dm
 * @Date: 2021/5/18 20:17
 * @Description:
 */
public class ShadowCloneTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        Student student = new Student();
        Address address = new Address("yn", "km", "gd");
        student.setName("张三");
        student.setAge(12);
        student.setAddress(address);

        Student student2 = student.clone();
//        Address address2 = new Address("yn","an","re");
        student2.setName("李四");
        Address student2Address = student2.getAddress();
//        student2.setAddress(address2);
        student2Address.setCity("an");

        System.out.println(student);
        System.out.println(student2);


    }
}
