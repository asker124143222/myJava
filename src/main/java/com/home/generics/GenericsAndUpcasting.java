package com.home.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xu.dm
 * @Date: 2021/3/2 16:27
 * @Version: 1.0
 * @Description: TODO
 **/


class Fruit {}

class Apple extends Fruit {}

class Orange extends Fruit {}

class GrannySmith extends Apple {}
class Gala extends Apple {}
class Fuji extends Apple {}
class Braeburn extends Apple {}

public class GenericsAndUpcasting {
    public static void main(String[] args) {
        List<Apple> apples = new ArrayList<>();
        apples.add(new GrannySmith());
        apples.add(new Gala());
        apples.add(new Fuji());
        apples.add(new Braeburn());
        for(Apple apple : apples)
            System.out.println(apple);
    }
}
