package com.yao.mq.activemq.provider;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by yaojian on 2022/1/17 15:49
 *
 * @author
 */
public class TopicProvider {

    public static void main(String[] args) {
        //1.创建连接工厂
        ConnectionFactory connectionFactory = null;
        //2.建立连接
        Connection connection = null;
        Session session = null;
        try {
            connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建会话
            session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
            //创建topic通道
            Destination topic = session.createTopic("topic.test");
            MessageProducer messageProducer = session.createProducer(topic);
            //设置持久化
            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
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
