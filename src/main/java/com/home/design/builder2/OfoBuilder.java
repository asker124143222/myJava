package com.home.design.builder2;

/**
 * @Author: xu.dm
 * @Date: 2021/5/23 11:08
 * @Description:
 */
public class OfoBuilder extends Builder {
    @Override
    public void buildFrame() {
        mBike.setFrame("黄色小家子");
    }

    @Override
    public void buildSeat() {
        mBike.setSeat("皮座");
    }

    @Override
    public Bike createBike() {
        return mBike;
    }
}
