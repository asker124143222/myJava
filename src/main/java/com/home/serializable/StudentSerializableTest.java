package com.home.serializable;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author: xu.dm
 * @Date: 2021/5/18 21:28
 * @Description:
 * 使用Jackson进行序列化，不需要类实现Serializable接口
 * 也不会调用writeObject和readObject方法
 */
public class StudentSerializableTest {
    public static void main(String[] args) throws Exception {
        Student student = new Student();
        Address address = new Address("yn", "km", "gd");
        student.setName("张三");
        student.setAge(12);
        student.setAddress(address);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(student);
        System.out.println(json);

        Student student2 = objectMapper.readValue(json, Student.class);
        student2.getAddress().setDetails("我在高大上");
        System.out.println(student2);

    }
}
