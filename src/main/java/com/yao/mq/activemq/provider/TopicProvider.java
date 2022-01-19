package com.yao.mq.activemq.provider;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by yaojian on 2022/1/17 15:49
 *
 * @author
 */
public class TopicProvider {
    //activemq同步模式会在消息是非事务模型下且消息采用持久化策略时使用，另外一种情况就是主动设置同步模式，其他情况都是默认异步模式

    public static void main(String[] args) {
        //1.创建连接工厂
        ActiveMQConnectionFactory connectionFactory = null;
        //2.建立连接
        Connection connection = null;
        Session session = null;
        try {
            connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();

            //设置发送消息异步过程(不需要等待broker返回对消息的处理情况)
            //connectionFactory.setUseAsyncSend(true);
            //回执窗口大小设置(异步发送超过这么多不能继续发送)
            //connectionFactory.setProducerWindowSize(10000);
            //启动连接
            connection.start();
            //创建会话
            session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
            //创建topic通道
            Destination topic = session.createTopic("topic.test");
            MessageProducer messageProducer = session.createProducer(topic);
            //设置消息持久化，默认是持久化，消息发送到broker上，producer会等待对这条消息发送的反馈
            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
            //非持久化设置，非持久化消息默认支持异步发送，需要设置setProducerWindowSize
            //如果需要每次消息都有回执的话
            //connectionFactory.setAlwaysSessionAsync(true);
            //messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage textMessage = session.createTextMessage("我是topic，欢迎大家2222222！");
            messageProducer.send(textMessage);
            //开启了事务，这个必须要加
            session.commit();
            System.out.println("已发送信息：" + textMessage.getText());

        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (session != null){
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

        }


    }


}
