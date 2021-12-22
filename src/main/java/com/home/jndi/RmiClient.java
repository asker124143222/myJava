package com.home.jndi;

import com.home.entity.User;

import java.rmi.Naming;
import java.rmi.Remote;

/**
 * @author: xu.dm
 * @since: 2021/12/22 14:11
 **/
public class RmiClient {
    public static void main(String[] args) {
        User answer;
        String userId = "00001";
        try {
            // lookup method to find reference of remote object
            UserService access = (UserService)Naming.lookup("rmi://localhost:1900/user");
            answer = access.findUser(userId);
            System.out.println("query:" + userId);
            System.out.println("result:" + answer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
