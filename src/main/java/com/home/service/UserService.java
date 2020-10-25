package com.home.service;

import com.home.entity.User;

/**
 * @Author: xu.dm
 * @Date: 2020/10/25 9:42
 * @Description:
 */
public interface UserService {
    User addUser(User user);
    User updateUser(User user);
    boolean deleteUser(Long userId);
    User getUser(Long userId);
}
