package com.home.spi;

/**
 * spi测试
 * 需在resources目录下建立META-INF/services目录，创建com.home.spi.Fruit文件,内容为该接口的具体实现类全限定名
 * 例如：com.home.spi.Apple
 * @author: xu.dm
 * @since: 2021/9/3 11:38
 **/
public interface Fruit {
    /**
     * 返回水果名称
     * @return 名字
     */
    String getName();
}
