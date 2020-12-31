package com.home.str;

import java.time.LocalDate;

/**
 * @Author: xu.dm
 * @Date: 2020/12/16 11:15
 * @Version: 1.0
 * @Description: TODO
 **/
public class StringFormatTest {
    public static void main(String[] args) {
        String content = "%s：两位好！\n" +
                "\n" +
                "《%s》已签，合同额%s元，其中涉及你部门研发费用%s，请知悉！\n" +
                "\n" +
                " 另，请明确项目经理、开发经理、实施经理人选并告知。\n" +
                "\n ---------------------------" +
                "\n\n  %s"+"\n  %s";

        String format = String.format(content, "张三","xxxxx","1234","222","bbb", LocalDate.now().toString());
        System.out.println(format);
        System.out.println("\n-------------------------\n");
        System.out.println(String.format("%03d", 19));
        System.out.println(String.format("%,d", 1234567890));
        System.out.println(String.format("%,.2f", 1234567890.12));
        System.out.println(String.format("%,.2f", 1234567890.00));
    }}
