package com.home.jndi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * @author: xu.dm
 * @since: 2021/12/22 14:21
 **/
public class RmiServer {
    public static void main(String[] args) {
        try {
            UserService userService = new UserServiceImpl();
            LocateRegistry.createRegistry(1900);
            Naming.rebind("rmi://localhost:1900/user", userService);
            System.out.println("start server,port is 1900");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
