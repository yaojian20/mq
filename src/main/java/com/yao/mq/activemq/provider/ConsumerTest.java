package com.yao.mq.activemq.provider;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by yaojian on 2022/1/17 10:40
 *
 * @author
 */
public class ConsumerTest {

    /**
     * activemq的消息消费默认情况下，mq服务器（broker）采用异步方式主动向客户端主动推送消息，
     * prefetchsize(预取消息数量)，每次推送都有一定的数量限制
     * queue持久化默认数量：1000，非持久化：1000
     * topic默认100 非持久化：32766
     * 假如prefetchsize = 0，对consumer来说就是一个pull模式
     *
     * 消息确认需要给broker发送一个ACK_TYPE，broker根据不同的ACK_TYPE决定应对策略
     *
     */

    public static void main(String[] args) {
        //tcp://localhost:61616可以再activemq里的activemq.xml里查到
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            //第一个参数表示是否开启事务，开启事务之后后面的参数不生效
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //消息目的地
            Destination queue = session.createQueue("activemq-common");
            //消息接收者
            MessageConsumer consumer = session.createConsumer(queue);
            TextMessage textMessage = (TextMessage)consumer.receive();
            System.out.println("消费者收到消息：" + textMessage.getText());
            System.out.println("message id is:" + textMessage.getJMSMessageID());
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
