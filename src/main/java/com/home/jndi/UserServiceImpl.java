package com.home.jndi;

import com.home.entity.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author: xu.dm
 * @since: 2021/12/22 14:18
 **/
public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    protected UserServiceImpl() throws RemoteException {
        System.out.println("UserServiceImpl ... ");
    }

    @Override
    public User findUser(String userId) throws RemoteException {
        // 加载在查询
        if ("00001".equals(userId)) {
            User user = new User();
            user.setUserName("金庸");
            user.setUserId(1L);
            user.setPassword("123456");
            return user;
        }
        throw new RemoteException("查无此人");
    }
}
