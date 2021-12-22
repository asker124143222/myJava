package com.home.jndi;

import com.home.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author: xu.dm
 * @since: 2021/12/22 14:16
 **/
public interface UserService extends Remote {
    User findUser(String userId) throws RemoteException;
}
