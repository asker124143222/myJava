package com.home.myProxy;

import com.home.entity.User;
import com.home.service.UserService;
import com.home.service.impl.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @Author: xu.dm
 * @Date: 2020/10/25 9:56
 * @Description: JDK动态代理第一种调用方式
 */
public class JdkProxyMain {
    public static void main(String[] args) {
        /**
         * JDK代理的核心就是反射包里的Proxy类，通过newProxyInstance方法生成动态代理类
         */
        UserService proxied = new UserServiceImpl();
        UserService userService = (UserService)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{UserService.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // proxy是动态生成代理对象，不要使用它里面的方法，会形成无限递归
                        System.out.println("进入JDK代理，被代理方法："+method.getName()+"，参数："+ Arrays.toString(args));
                        // invoke调用的第一个参数是被代理的对象，传入的方式无所谓，本例中使用了匿名类，所以必须从外部进入。
                        Object result = method.invoke(proxied, args);
                        System.out.println("完成代理，返回值："+result);
                        return result;
                    }
                });

        User user = new User();
        user.setUserId(1L);
        user.setUserName("admin");
        userService.addUser(user);
        userService.updateUser(user);
        userService.deleteUser(user.getUserId());
    }
}
