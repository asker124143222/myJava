package com.home.enums;

import java.util.function.BiFunction;

/**
 * @Author: xu.dm
 * @Date: 2021/1/13 11:29
 * @Version: 1.0
 * @Description: TODO
 **/
public class EnumTest {
    public static void main(String[] args) {
        int r1 = Operation.ADD.apply(1, 2);
        int r2 = Operation.MINUS.apply(100, 2);
        System.out.println(r1 +" --- " +r2);
    }
}

enum Operation {
    ADD((x,y) -> x + y),
    MINUS((x,y) -> x -y);

    Operation(BiFunction<Integer,Integer,Integer> operation) {
        this.operation = operation;
    }

    private final BiFunction<Integer, Integer, Integer> operation;

    public int apply(int x, int y) {
        // operation.apply指向函数式接口BiFunction的apply方法
        return operation.apply(x, y);
    }
}
