package com.home.service;

/**
 * @Author: xu.dm
 * @Date: 2020/10/25 12:37
 * @Description:
 */
public class LoginService {
    public boolean Login(String username,String password){
        System.out.println("Login --> username:"+username+", password:"+password);
        return true;
    }

    public void logout(String username){
        System.out.println("logout --> "+username);
    }
}
