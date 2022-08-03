package com.home.generics;

import com.home.entity.Role;
import com.home.entity.User;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author xu.dm
 * @since 2022/4/7 20:50
 * 获取泛型类中的具体参数类型
 * 在Spring中可以使用GenericTypeResolver来获取泛型参数
 */
public class GenericClassType {
    public static void main(String[] args) {
        Type type = UserDo.class.getGenericSuperclass();
        System.out.println(type);

        if(type instanceof ParameterizedType){
            Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
            System.out.println(Arrays.toString(arguments));
        }


    }

    static class BaseDO<T,E> {
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        public LocalDateTime getCreateTime() {
            return createTime;
        }

        public void setCreateTime(LocalDateTime createTime) {
            this.createTime = createTime;
        }

        public LocalDateTime getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
        }

        @Override
        public String toString() {
            return "BaseDO{" +
                    "createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    '}';
        }
    }

    static class UserDo extends BaseDO<User, Role> {

    }
}
