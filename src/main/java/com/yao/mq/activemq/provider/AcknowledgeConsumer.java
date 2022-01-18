package com.yao.mq.activemq.provider;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by yaojian on 2022/1/18 10:50
 *
 * @author
 */
public class AcknowledgeConsumer {


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
            MessageConsumer messageConsumer = session.createConsumer(destination);
            for (int i =0; i < 10; i++){
                TextMessage message = (TextMessage) messageConsumer.receive();
                System.out.println(message.getText());
                if (i == 5){
                    //0-5的消息都会被确认消费
                    message.acknowledge();
                }
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
