package com.yao.mq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;

/**
 * Created by yaojian on 2022/1/13 15:57
 *
 * @author
 */
@Configuration
public class RabbiMqConfig {

    private final static String USERNAME = "guest";
    private final static String PASSWORD = "guest";
    private final static String HOST = "localhost";
    private final static Integer PORT = 5672;



    @Bean
    //创建连接工厂
    public ConnectionFactory connectionFactory() throws IOException {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
//        factory.setVirtualHost("test");
        factory.setHost(HOST);
        factory.setPort(Integer.valueOf(PORT));
        //factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
        factory.setPublisherConfirms(true);
        //factory.createConnection().createChannel(false).queueDeclare(planQueue, true, false, false, null);
        return factory;
    }


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    // 必须是prototype类型-->如果消费者或者他自己
    public RabbitTemplate rabbitTemplate() throws IOException {
        return new RabbitTemplate(connectionFactory());
    }

    //消费者监听器工厂
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() throws IOException {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        //设置手动ack
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }





}
