package com.home.innerClass;

/**
 * @Author: xu.dm
 * @Date: 2021/2/24 9:41
 * @Version: 1.0
 * @Description: 迭代器接口
 **/
public interface Selector {
    boolean end();
    Object current();
    void next();

}
