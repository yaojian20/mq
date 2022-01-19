package com.yao.mq.activemq.provider;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by yaojian on 2022/1/17 10:04
 *
 * @author
 */
public class ProducerTest {
    /**
     * activemq支持的协议TCP、UDP、SSL、NIO、HTTP/HTTPS、vm
     * activeMq持久化存储kahaDB 、AMQ message store、jdbc、memory
     * kahaDB 默认的存储方式
     * AMQ 基于文件的存储方式 写入速度很快，容易恢复 文件默认大小32M
     * jdbc 基于数据库的存储方式 需要设置bean，里面是datasource
     * memory 基于内存的存储方式
     *
     * level DB 5.8以后引入的持久化策略，用于集群配置
     * 在activemq.xml里修改
     */


    /**
     * activeMQ网络连接
     * activeMq如果要实现拓展性跟高可用性的话，需要用到网络连接将多个broker连接到一起
     * networkConnector->主要用来配置broker与broker之间的通信连接
     * 两种方式：1.静态网络连接 2.动态网络连接
     * 1.静态网络连接
     * 丢失消息（其实是集群环境下消息被集群其他机器消费但是没消费完，此时消息滞留在其他机器，这时候消息无法消费）-》解决 消息回流
     *
     *
     *
     *
     */

    /**
     * activemq消息丢失的情况
     * 使用同步模式的时候，有3种状态保证消息被安全生产，在配置为1（只保证写入leader成功）的话，如果刚好leader partition挂了，数据就会丢失。
     * 还有一种情况可能会丢失消息，就是使用异步模式的时候，当缓冲区满了，如果配置为0（还没有收到确认的情况下，缓冲池一满，就清空缓冲池里的消息），数据就会被立即丢弃掉
     * 在数据生产时避免数据丢失的方法： 只要能避免上述两种情况，那么就可以保证消息不会被丢失。
     * 在同步模式的时候，确认机制设置为-1，也就是让消息写入leader和所有的副本。
     * 在异步模式下，如果消息发出去了，但还没有收到确认的时候，缓冲池满了，在配置文件中设置成不限制阻塞超时的时间，也就说让生产端一直阻塞，这样也能保证数据不会丢失。
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
             //消息体
             TextMessage textMessage = session.createTextMessage("hello world!");
             //消息发送者
             MessageProducer producer = session.createProducer(queue);
             //发送消息
             producer.send(textMessage);
            System.out.println("message id is:" + textMessage.getJMSMessageID());
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
