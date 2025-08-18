package com.home.basic;


import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author: xu.dm
 * @since: 2025/7/30 10:10
 **/
public class MathTest {

    public static void main(String[] args) {
        double a = 12.131212;
        double b = 15.010345;

        BigDecimal a1 = new BigDecimal(a);
        BigDecimal b1 = new BigDecimal(b);

        a = a * 1000;
        b = b * 1000;
        a = Math.floor(a);
        b = Math.floor(b);

        System.out.println(a);
        System.out.println(b);

        a = a / 10;
        b = b /10;
        System.out.println(a);
        System.out.println(b);

        a = Math.ceil( a);
        b = Math.ceil( b);

        a = a / 100;
        b = b /100;
        System.out.println(a);
        System.out.println(b);

        System.out.println("---------------------------");
        double a2 = roundThirdDecimal(a1.doubleValue());
        double b2 = roundThirdDecimal(b1.doubleValue());
        System.out.println(a1.doubleValue());
        System.out.println(b1.doubleValue());
        System.out.println(a2);
        System.out.println(b2);


    }

    /**
     * 对小数点后第三位进行特殊舍入处理
     * 第三位非零则进位，第三位为零则直接舍弃
     */
    public static double roundThirdDecimal(double value) {
        BigDecimal bd = new BigDecimal(String.valueOf(value));
        String strValue = bd.toPlainString();

        // 查找小数点位置
        int dotIndex = strValue.indexOf('.');
        if (dotIndex == -1 || strValue.length() <= dotIndex + 3) {
            // 没有小数点或小数位数不足三位，直接返回原值
            return value;
        }

        // 获取第三位小数
        char thirdDecimal = strValue.charAt(dotIndex + 3);

        if (thirdDecimal == '0') {
            // 第三位为0，直接截断到第二位
            return bd.setScale(2, RoundingMode.DOWN).doubleValue();
        } else {
            // 第三位非0，向第二位进位
            return bd.setScale(2, RoundingMode.UP).doubleValue();
        }
    }

}
