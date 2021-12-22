package com.home.jndi;

import java.rmi.Naming;

/**
 * @author: xu.dm
 * @since: 2021/12/22 14:11
 **/
public class RmiClient2 {
    public static void main(String[] args) {
        try {
            // lookup method to find reference of remote object
            Naming.lookup("rmi://localhost:2048/example");
            Thread.sleep(10000);
            System.out.println("正常结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
