package com.yao.mq.rabbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        //设置为true才会调用回调函数
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                //调用情况
                //1.消息推送到server，但是在server里找不到交换机
                //2.消息推送到server，找到交换机了，但是没找到队列
                //3.消息推送到sever，交换机和队列啥都没找到
                //4.消息推送成功
                System.out.println("ConfirmCallback:     "+"相关数据："+ correlationData);
                System.out.println("ConfirmCallback:     "+"确认情况："+ ack);
                System.out.println("ConfirmCallback:     "+"原因："+ cause);
            }
        });
        //已过时
        //rabbitTemplate.setReturnCallback();
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                //2.消息推送到server，找到交换机了，但是没找到队列
                System.out.println("ReturnsCallback :" + returnedMessage.toString());
            }
        });
        return rabbitTemplate;
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
