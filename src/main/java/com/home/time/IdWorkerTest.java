package com.home.time;

import java.util.Date;

/**
 * @author xu.dm
 * @since 2023/11/7 11:02
 **/
public class IdWorkerTest {
    public static void main(String[] args) {
        long bp = 1288834974657L;
        // 2023/11/7 11:40:xx
        long id1 = 1721734237923704832L;

        String id1Binary = Long.toBinaryString(id1);

        System.out.println(id1Binary);

        String ts = "01111101001100001111110100010110100100000";
        long ts2 = 1075385609504L+bp;
        long timespan = (id1 >> 22L) + bp;

        System.out.println(" "+Long.toBinaryString(timespan));

        System.out.println(timespan);

        Date date = new Date(timespan);

        System.out.println(date);

        Date date2 = new Date(ts2);
        System.out.println(date2);

        Date date3 = new Date(bp);
        System.out.println(date3);

    }
}
