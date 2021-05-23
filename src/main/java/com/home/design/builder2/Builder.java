package com.home.design.builder2;

/**
 * @Author: xu.dm
 * @Date: 2021/5/23 11:03
 * @Description:
 */
public abstract class Builder {
    protected Bike mBike = new Bike();

    public abstract void buildFrame();
    public abstract void buildSeat();
    public abstract Bike createBike();
}
