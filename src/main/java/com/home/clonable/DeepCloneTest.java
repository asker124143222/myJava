package com.home.clonable;

/**
 * @Author: xu.dm
 * @Date: 2021/5/18 20:12
 * @Description:
 */
public class DeepCloneTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        StudentDeep student = new StudentDeep();
        Address address = new Address("yn", "km", "gd");
        student.setName("张三");
        student.setAge(12);
        student.setAddress(address);

        StudentDeep student2 = student.clone();
        Address address2 = student2.getAddress();
        address2.setCity("an");

        System.out.println(student);
        System.out.println(student2);
    }
}
