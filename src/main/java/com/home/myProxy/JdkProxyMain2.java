package com.home.myProxy;

import com.home.entity.User;
import com.home.service.UserService;
import com.home.service.impl.UserServiceImpl;

/**
 * @Author: xu.dm
 * @Date: 2020/10/25 10:32
 * @Description: jdk动态代理第二种方式，通过实现InvocationHandler接口封装动态代理所需各类要素
 *
 */
public class JdkProxyMain2 {
    public static void main(String[] args) {
        UserService userService = (UserService)new ProxyByJDK().getProxyInstance(new UserServiceImpl());
        User user = new User();
        user.setUserId(1L);
        user.setUserName("admin");
        userService.addUser(user);
        userService.updateUser(user);
        userService.deleteUser(user.getUserId());
    }
}
