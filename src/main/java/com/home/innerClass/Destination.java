package com.home.innerClass;

/**
 * @Author: xu.dm
 * @Date: 2021/2/23 11:04
 * @Version: 1.0
 * @Description: 普通接口，供内部类测试使用
 **/
public interface Destination {
    default String readLabel() {
        return null;
    }
}
