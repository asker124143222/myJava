package com.home.myProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @Author: xu.dm
 * @Date: 2020/10/25 10:33
 * @Description:
 * 1、这个类的目的就是独立实现Proxy.newInstance所需的第三个参数InvocationHandler接口
 * 2、生成动态代理对象
 */
public class ProxyByJDK implements InvocationHandler {
    // 被代理对象，外部传入
    private Object proxied;

    /**
     *
     * @param proxied 被代理的对象
     * @return 动态生成的代理对象
     */
    public Object getProxyInstance(Object proxied){
        this.proxied = proxied;
        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                proxied.getClass().getInterfaces(),
                this);
    }

    /**
     *
     * @param clazz 被代理的类
     * @return 代理对象
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object getProxyInstance(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        Object proxied = clazz.newInstance();
        this.proxied = proxied;
        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                proxied.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("进入JDK代理，被代理方法："+method.getName()+"，参数："+ Arrays.toString(args));
        Object result = method.invoke(proxied, args);
        System.out.println("完成代理，返回值："+result);
        return result;
    }
}
