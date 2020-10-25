package com.home.util;

import com.home.MyAnnotation.Permission;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: xu.dm
 * @Date: 2020/10/25 14:40
 * @Description:
 */
public class AuthUtils {
    public static String[] getAuthorities(){
        // 模拟当前用户所具备的权限
        return new String[]{"user:add","user:update"};
    }

    // 判断是否具备方法权限
    public static boolean hasAnyPermissions(Method method){
        List<String> currentAuthorites = null;
        if (null != method.getAnnotation(Permission.class)) {
            currentAuthorites = Arrays.asList(method.getAnnotation(Permission.class).value());
        }
        if(currentAuthorites == null || currentAuthorites.size() ==0) return true;

        List<String> hasPermissions = Arrays.asList(getAuthorities());
        System.out.println("权限检查，需要权限："+currentAuthorites);
        return hasPermissions.stream().anyMatch(currentAuthorites::contains);
    }
}
