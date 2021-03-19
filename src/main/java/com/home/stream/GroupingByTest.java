package com.home.stream;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: xu.dm
 * @Date: 2021/3/19 21:31
 * @Description:
 */
public class GroupingByTest {
    public static void main(String[] args) {

        Product prod1 = new Product(1L, 1, new BigDecimal("15.5"), "面包", "零食");
        Product prod2 = new Product(2L, 2, new BigDecimal("20"), "饼干", "零食");
        Product prod3 = new Product(3L, 3, new BigDecimal("30"), "月饼", "零食");
        Product prod4 = new Product(4L, 3, new BigDecimal("10"), "青岛啤酒", "啤酒");
        Product prod5 = new Product(5L, 10, new BigDecimal("15"), "百威啤酒", "啤酒");
        Product prod6 = new Product(6L, 20, new BigDecimal("25"), "百威啤酒", "啤酒");
        List<Product> prodList = new ArrayList<>();
        prodList.add(prod1);
        prodList.add(prod2);
        prodList.add(prod3);
        prodList.add(prod4);
        prodList.add(prod5);
        prodList.add(prod6);

        Map<String, List<Product>> collect = prodList.stream().collect(Collectors.groupingBy(item -> item.getCategory() + "_" + item.getName()));

        collect.forEach((s, products) -> {
            System.out.println(s);
            products.forEach(System.out::println);
            System.out.println("----------");
        });



    }


}
