package com.home.regex;

/**
 * @author: xu.dm
 * @since: 2021/12/29 16:31
 **/
public class StrongPassword {
    private static final String REGEX_NUM = ".*\\d.*";
    private static final String REGEX_UPPER = ".*[A-Z].*";
    private static final String REGEX_DOWN = ".*[a-z].*";
    private static final String REGEX_SPECIAL = ".*\\W.*";
    public static void main(String[] args) {
        testPassword("123456");
        System.out.println("---------");
        testPassword("abcd123");
        System.out.println("---------");
        testPassword("Abcd123");
        System.out.println("---------");
        testPassword("a4zJC!");
        System.out.println("---------");
        testPassword("9u9N57P!h&");
        System.out.println("---------");
        testPassword("ck~4v7TM2[R?swi2:4xO");

    }

    private static int testPassword(String password){
        System.out.println(password);
        int minMode = 3;
        int maxStrength = 12;
        int minStrength = 6;
        int modes = 0;
        if (password.length() < minStrength) {
            return 0;
        }
        if(password.matches(REGEX_NUM)){
            System.out.println("包括数字");
            modes++;
        }
        if(password.matches(REGEX_UPPER)){
            System.out.println("包括大写字母");
            modes++;
        }
        if(password.matches(REGEX_DOWN)){
            System.out.println("包括小写字母");
            modes++;
        }
        if(password.matches(REGEX_SPECIAL)){
            System.out.println("包括特殊字符");
            modes++;
        }
        if(modes > minMode && password.length() > maxStrength){
            System.out.println("长度大于"+maxStrength);
            modes++;
        }

//        if(Pattern.matches(""))
//        if (/\d/.test(val)) modes++ // 数字
//        if (/[a-z]/.test(val)) modes++ // 小写
//        if (/[A-Z]/.test(val)) modes++ // 大写
//        if (/\W/.test(val)) modes++ // 特殊字符
//        if (modes > 3 && val.length > 12) modes++
        return modes;
    }
}
