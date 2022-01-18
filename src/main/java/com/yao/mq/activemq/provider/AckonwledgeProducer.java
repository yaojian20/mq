package com.yao.mq.activemq.provider;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by yaojian on 2022/1/18 10:43
 *
 * @author
 */
public class AckonwledgeProducer {


    public static void main(String[] args) {
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        try {
            connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            connection.start();
            //设置关闭本地事务，开启消费端应答
            session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
            Destination destination = session.createQueue("ack_test");
            MessageProducer messageProducer = session.createProducer(destination);
            for (int i =0; i < 10; i++){
                TextMessage message = session.createTextMessage("hello, 我是ack" + i);
                messageProducer.send(message);
            }

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
