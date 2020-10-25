package com.home.myProxy;

import com.home.util.AuthUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: xu.dm
 * @Date: 2020/10/25 14:55
 * @Description: 通过动态代理，模拟权限验证
 */
public class ProxyAuthority implements MethodInterceptor {

    public Object getProxyInstance(Class<?> clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        boolean hasAnyPermissions = AuthUtils.hasAnyPermissions(method);
        if(hasAnyPermissions){
            System.out.println("通过权限审核..."+method.getName());
            Object result = proxy.invokeSuper(obj,args);
            return result;
        }else {
            System.out.println("没有权限调用该方法..."+method.getName());
            return null;
        }
    }
}
