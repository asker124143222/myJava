package com.home.design.builder2;

/**
 * @Author: xu.dm
 * @Date: 2021/5/23 11:09
 * @Description:
 */
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public Bike construct() {
        builder.buildFrame();
        builder.buildSeat();
        return builder.createBike();
    }
}
