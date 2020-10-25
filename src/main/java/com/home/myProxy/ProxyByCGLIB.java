package com.home.myProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: xu.dm
 * @Date: 2020/10/25 13:02
 * @Description: cglib的核心对象是Enhancer，用于生成被代理对象的子类
 */
public class ProxyByCGLIB implements MethodInterceptor {

    public Object getProxyInstance(Class<?> clazz){
        Enhancer enhancer = new Enhancer();
        // 设置要代理的类
        enhancer.setSuperclass(clazz);
        // 设置回调的对象
        enhancer.setCallback(this);
        return enhancer.create();
    }
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("进入cglib代理，被代理方法："+method.getName()+",参数："+ Arrays.toString(args));
        // 如果MethodProxy调用参数第一个是代理对象本身，需要调用其父类的方法，因为cglib是动态生成一个子类
        Object result = proxy.invokeSuper(obj, args);
        // 注意与上面方法入参的区别, proxy.invoke的入参必须是被代理的对象，这种方式耦合度太高，而且多创建了一个对象
//        Object result2 = proxy.invoke(proxied, args);

        System.out.println("代理结束，返回值："+result);
        return result;
    }
}
