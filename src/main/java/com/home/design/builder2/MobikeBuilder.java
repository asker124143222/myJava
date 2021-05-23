package com.home.design.builder2;

/**
 * @Author: xu.dm
 * @Date: 2021/5/23 11:04
 * @Description:
 */
public class MobikeBuilder extends Builder {
    @Override
    public void buildFrame() {
        mBike.setFrame("铝合金车架");
    }

    @Override
    public void buildSeat() {
        mBike.setSeat("真皮车座");
    }

    @Override
    public Bike createBike() {
        return mBike;
    }
}
