package com.home.service.impl;

import com.home.MyAnnotation.Permission;
import com.home.entity.User;
import com.home.service.UserService;

/**
 * @Author: xu.dm
 * @Date: 2020/10/25 9:43
 * @Description:
 */
public class UserServiceImpl implements UserService {

    @Override
    @Permission("user:add")
    public User addUser(User user) {
        System.out.println("addUser --> "+user);
        user.setPassword("addUser");
        return user;
    }

    @Override
    @Permission("user:update")
    public User updateUser(User user) {
        System.out.println("updateUser --> "+user);
        user.setPassword("updateUser");
        return user;
    }

    @Override
    @Permission("user:delete")
    public boolean deleteUser(Long userId) {
        System.out.println("deleteUser --> "+userId);
        return true;
    }

    @Override
    public User getUser(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setUserName("xu.dm");
        user.setPassword("123456");
        return user;
    }
}
