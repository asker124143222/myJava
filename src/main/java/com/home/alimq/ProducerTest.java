package com.home.alimq;

import com.aliyun.openservices.ons.api.*;

import java.util.Date;
import java.util.Properties;

/**
 *
 * @author xu.dm
 * @since 2023/8/3 15:25
 **/
public class ProducerTest {
    public static void main(String[] args) {
        String ak = "uKdpuonbgH7IbO1x";
        String sk = "AbWyZLA0bxdwEpv6MBgdcBx6sf4G59";
        String nameSrvAddr = "http://MQ_INST_1711488696525962_BieYEKtM.cn-kunming-ynyc-d01.mq.namesrv.res.ynyc.com:9876";
        // String nameSrvAddr = "10.202.217.185:9876";
        String topic = "topicWood3";
        String tags = "TagA";
        int count = 10;
        Properties properties = new Properties();
        // AccessKey 阿⾥云⾝份验证，在阿⾥云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, ak);
        // SecretKey 阿⾥云⾝份验证，在阿⾥云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, sk);
        // 设置发送超时时间，单位毫秒
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        // 设置 TCP 接⼊域名，进⼊控制台的实例管理⻚⾯的“获取接⼊点信息”区域查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR,nameSrvAddr);
        Producer producer = ONSFactory.createProducer(properties);
        // 在发送消息前，必须调⽤ start ⽅法来启动 Producer，只需调⽤⼀次即可
        producer.start();
        //循环发送消息
        for (int i = 0; i < count; i++) {
            Message msg = new Message(
                // Message 所属的 Topic
                    topic,
                // Message Tag 可理解为 Gmail 中的标签，对消息进⾏再归类，⽅便 Consumer 指定过滤条件在 RocketMQ 的服务器过滤
                    tags,
                // Message Body 可以是任何⼆进制形式的数据， RocketMQ 不做任何⼲预，
                // 需要 Producer 与 Consumer 协商好⼀致的序列化和反序列化⽅式
                    (String.valueOf(i) + ". 解释命令explain MQ").getBytes());
                // 设置代表消息的业务关键属性，请尽可能全局唯⼀。
                // 以⽅便您在⽆法正常收到消息情况下，可通过阿⾥云服务器管理控制台查询消息并补发
                // 注意：不设置也不会影响消息正常收发
                 msg.setKey("order_id" + i);
            try {
                SendResult sendResult = producer.send(msg);
                // 同步发送消息，只要不抛异常就是成功
                if (sendResult != null) {
                    System.out.println(new Date() + " Send mq message success. Topic is:" +
                            msg.getTopic() + " msgId is: " + sendResult.getMessageId());
                }
            } catch (Exception e) {
                // 消息发送失败，需要进⾏重试处理，可重新发送这条消息或持久化这条数据进⾏补偿处理
                System.out.println(new Date() + " Send mq message failed. Topic is:" + msg.
                        getTopic());
                e.printStackTrace();
            }
        }
        // 在应⽤退出前，销毁 Producer 对象
        // 注意：如果不销毁也没有问题
        producer.shutdown();
    }

}
