package com.yao.mq.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * Created by yaojian on 2022/1/19 15:07
 *
 * @author
 */
@Configuration
public class ActiveMqConfig {

    private static final String USER_NAME = "admin";

    private static final String PASSWORD = "admin";

    private static final String BROKER_URL = "tcp://localhost:61616";

    public static final String QUEUE_NAME = "springboot-queue";

    public static final String TOPIC_NAME = "springboot_topic";

    //连接工厂
    @Bean
    ActiveMQConnectionFactory activeMQConnectionFactory(){
        return  new ActiveMQConnectionFactory(USER_NAME, PASSWORD, BROKER_URL);
    }

    //p2p
    @Bean
    public Queue queue(){
        return new ActiveMQQueue(QUEUE_NAME);
    }

    //topic
    @Bean
    public Topic topic(){
        return new ActiveMQTopic(TOPIC_NAME);
    }

    //P2P消息监听工厂
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ActiveMQConnectionFactory activeMQConnectionFactory){
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(activeMQConnectionFactory);
        return bean;
    }

    //topic消息监听工厂
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory activeMQConnectionFactory){
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        //设置为发布订阅方式, 默认情况下使用的生产消费者方式
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(activeMQConnectionFactory);
        return bean;
    }


}
