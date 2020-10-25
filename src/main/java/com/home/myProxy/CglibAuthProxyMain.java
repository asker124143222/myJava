package com.home.myProxy;

import com.home.entity.User;
import com.home.factory.UserServiceFactory;
import com.home.service.UserService;
import com.home.service.impl.UserServiceImpl;

/**
 * @Author: xu.dm
 * @Date: 2020/10/25 15:03
 * @Description:
 */
public class CglibAuthProxyMain {
    public static void main(String[] args) {
        // 实际操作中，这里应该使用工厂模式屏蔽UserService的获取细节
//        UserService userService = (UserService) new ProxyAuthority().getProxyInstance(UserServiceImpl.class);
        UserService userService = UserServiceFactory.getUserServiceProxy();
        User user = new User();
        user.setUserId(123L);
        user.setUserName("admin");
        user.setPassword("123456");
        userService.addUser(user);
        userService.updateUser(user);
        userService.deleteUser(123L);
        userService.getUser(456L);
    }
}

