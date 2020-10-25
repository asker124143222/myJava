package com.home.myProxy;

import com.home.entity.User;
import com.home.service.LoginService;
import com.home.service.UserService;
import com.home.service.impl.UserServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: xu.dm
 * @Date: 2020/10/25 13:37
 * @Description: 第二种方式
 */
public class CglibProxyMain2 {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(LoginService.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("进入cglib代理，被代理方法："+method.getName()+",参数："+ Arrays.toString(args));
                // obj是代理对象，所以必须用invokeSuper调用父类的方法
                // 如果是proxy.invoke，那么入参必须是被代理对象
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("代理结束，返回值："+result);
                return result;
            }
        });
        LoginService loginService = (LoginService) enhancer.create();
        loginService.Login("admin","123");
        loginService.logout("admin");
        System.out.println();
        System.out.println("-----------------");


        enhancer = new Enhancer();
        enhancer.setSuperclass(UserServiceImpl.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("进入cglib代理，被代理方法："+method.getName()+",参数："+ Arrays.toString(args));
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("代理结束，返回值："+result);
                return result;
            }
        });
        UserService userService = (UserService) enhancer.create();
        User user = new User();
        user.setUserName("admin");
        userService.addUser(user);
        userService.updateUser(user);
        userService.deleteUser(123L);
    }
}
