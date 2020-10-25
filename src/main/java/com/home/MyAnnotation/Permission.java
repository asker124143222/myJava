package com.home.MyAnnotation;

import java.lang.annotation.*;

/**
 * @Author: xu.dm
 * @Date: 2020/10/25 14:28
 * @Description: 权限注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permission {
    String[] value();
}
