package com.yao.mq.activemq.provider;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by yaojian on 2022/1/17 16:24
 *
 * @author
 */
public class TopicConsumer1 {

    public static void main(String[] args) {

        //tcp://localhost:61616可以再activemq里的activemq.xml里查到
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            //创建持久订阅
            connection.setClientID("1-1-1-1-1");
            connection.start();
            //第一个参数表示是否开启事务，开启事务之后后面的参数不生效
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //消息目的地
            Topic topic = session.createTopic("topic.test");
            //创建持久订阅
            TopicSubscriber consumer = session.createDurableSubscriber(topic, "consumer1");
            TextMessage textMessage = (TextMessage)consumer.receive();
            System.out.println("topic1消费者收到消息：" + textMessage.getText());
            //确认收到消息
            session.commit();
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
