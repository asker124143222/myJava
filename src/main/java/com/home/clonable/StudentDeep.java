package com.home.clonable;

import java.io.*;

/**
 * @Author: xu.dm
 * @Date: 2021/5/18 20:14
 * @Description:
 */
public class StudentDeep implements Cloneable,Serializable {
    private String name;
    private int age;
    private Address address;

    public StudentDeep() {
    }

    public StudentDeep(String name, int age, Address address) {
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

    @Override
    protected StudentDeep clone() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(out);
            outputStream.writeObject(this);

            byte[] bytes = out.toByteArray();
            outputStream.close();

            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            ObjectInputStream inputStream = new ObjectInputStream(input);
            StudentDeep studentDeep = (StudentDeep) inputStream.readObject();
            inputStream.close();
            return studentDeep;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

//    private void writeObject(ObjectOutputStream out) throws IOException {
//        System.out.println("writeObject");
//        out.writeObject(this);
//    }
//
//    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//        System.out.println("readObject");
//        in.readObject();
//    }
}
