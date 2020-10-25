package com.home.factory;

import com.home.myProxy.ProxyAuthority;
import com.home.service.UserService;
import com.home.service.impl.UserServiceImpl;

/**
 * @Author: xu.dm
 * @Date: 2020/10/25 15:23
 * @Description:
 */
public class UserServiceFactory {
    public static UserService getUserServiceProxy(){
        UserService userService = (UserService) new ProxyAuthority().getProxyInstance(UserServiceImpl.class);
        return userService;
    }
}
