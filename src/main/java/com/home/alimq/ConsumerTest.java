package com.home.alimq;

import com.aliyun.openservices.ons.api.*;

import java.util.Properties;

/**
 * @author xu.dm
 * @since 2023/8/3 15:34
 **/
public class ConsumerTest {
    public static void main(String[] args) {
        String ak = "uKdpuonbgH7IbO1x";
        String sk = "AbWyZLA0bxdwEpv6MBgdcBx6sf4G59";
        String nameSrvAddr = "http://MQ_INST_1711488696525962_BieYEKtM.cn-kunming-ynyc-d01.mq.namesrv.res.ynyc.com:9876";
        String topic = "topicWood3";
        String topic2 = "topicWood2";
        String tagA = "TagA";
        String tagB = "TagB";
        String groupId = "GID-wood-cloud3-project-user-consumer";
        int count = 10;
        Properties properties = new Properties();
        // 您在控制台创建的 Group ID
        properties.put(PropertyKeyConst.GROUP_ID, groupId);
        // AccessKey 阿⾥云⾝份验证，在阿⾥云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, ak);
        // SecretKey 阿⾥云⾝份验证，在阿⾥云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, sk);
        // 设置 TCP 接⼊域名，进⼊控制台的实例管理⻚⾯的“获取接⼊点信息”区域查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR,nameSrvAddr);
        // 集群订阅⽅式 (默认)
        // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
        // ⼴播订阅⽅式
        // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe(topic, "TagA||TagB", new MessageListener() {
            //订阅多个 Tag
            @Override
            public Action consume(Message message, ConsumeContext context) {
                System.out.println("Receive: " + message);
                System.out.println(new String(message.getBody()));
                return Action.CommitMessage;
            }
        });
        //订阅另外⼀个 Topic
        consumer.subscribe(topic2, "*", new MessageListener() {
            //订阅全部 Tag
            @Override
            public Action consume(Message message, ConsumeContext context) {
                System.out.println("Receive: " + message);
                System.out.println(new String(message.getBody()));
                return Action.CommitMessage;
            }
        });
        consumer.start();
        System.out.println("Consumer Started");
    }

}
