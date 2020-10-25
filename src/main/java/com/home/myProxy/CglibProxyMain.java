package com.home.myProxy;

import com.home.service.LoginService;

/**
 * @Author: xu.dm
 * @Date: 2020/10/25 12:35
 * @Description:
 */
public class CglibProxyMain {
    public static void main(String[] args) {
        LoginService loginService = (LoginService) new ProxyByCGLIB().getProxyInstance(LoginService.class);
        loginService.Login("admin","123");
        loginService.logout("admin");

    }
}
