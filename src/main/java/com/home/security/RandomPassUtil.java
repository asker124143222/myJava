package com.home.security;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 生成随机密码
 * @author: xu.dm
 * @since: 2021/12/29 15:34
 **/
public class RandomPassUtil {
    private static final int MAX_LENGTH = 20;
    private static final int MIN_LENGTH = 6;
    private static final String LOW_STR = "abcdefghijkmnopqrstuvwxyz";
    private static final String SPECIAL_STR = "~!@#$%^&*()_+/-=[]{};:'<>?.";
    private static final String numStr = "023456789";

    /**
     * 随机获取字符串字符
     * @param str 候选字符串
     * @return 随机获取字符串字符
     */
    private static char getRandomChar(String str) {
        SecureRandom random = new SecureRandom();
        return str.charAt(random.nextInt(str.length()));
    }


    /**
     * 随机获取小写字符
     * @return 随机获取小写字符
     */
    private static char getLowChar() {
        return getRandomChar(LOW_STR);
    }


    /**
     * 随机获取大写字符
     * @return 随机获取大写字符
     */
    private static char getUpperChar() {
        return Character.toUpperCase(getLowChar());
    }


    /**
     * 随机获取数字字符
     * @return 随机获取数字字符
     */
    private static char getNumChar() {
        return getRandomChar(numStr);
    }


    /**
     * 随机获取特殊字符
     * @return 随机获取特殊字符
     */
    private static char getSpecialChar() {
        return getRandomChar(SPECIAL_STR);
    }


    /**
     * 指定调用字符函数
     * @param funNum 0-2，随机调用函数
     * @return 随机字符
     */
    private static char getRandomChar(int funNum) {
        switch (funNum) {
            case 0:
                return getLowChar();
            case 1:
                return getUpperChar();
            case 2:
                return getNumChar();
            default:
                return getSpecialChar();
        }
    }


    /**
     * 指定长度，随机生成复杂密码
     * @param num 密码长度
     * @return 生成随机字符串
     */
    public static String generatePassword(int num) {
        if (num > MAX_LENGTH || num < MIN_LENGTH) {
           throw new RuntimeException("密码长度不符合[6-20]位的要求");
        }
        List<Character> list = new ArrayList<>(num);
        list.add(getLowChar());
        list.add(getUpperChar());
        list.add(getNumChar());
        list.add(getSpecialChar());

        for (int i = 4; i < num; i++) {
            SecureRandom random = new SecureRandom();
            int funNum = random.nextInt(4);
            list.add(getRandomChar(funNum));
        }

        // 打乱排序
        Collections.shuffle(list);
        StringBuilder stringBuilder = new StringBuilder(list.size());
        for (Character c : list) {
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(generatePassword(6));
        System.out.println(generatePassword(10));
        System.out.println(generatePassword(20));
    }
}
