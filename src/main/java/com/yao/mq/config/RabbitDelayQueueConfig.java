package com.yao.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yaojian on 2022/1/14 17:26
 *
 * @author
 */
//rabbitmq里的延时队列可以通过死信队列来实现
//rabbitmq可以将一个消息发到A消息队列中，并设置过期时间，然后到了过期时间还没有消费者消费该消息的时候，就将该消息转发给死信队列中并从a队列中删除
//然后死信队列来处理该消息
@Configuration
public class RabbitDelayQueueConfig {

    //
    public static final String DELAY_DELETE_EXCHANGE = "delay_test_delete_exchange";
    public static final String DELAY_EXCHANGE = "delay_test_exchange";

    public static final String DELAY_DELETE_QUEUE = "delay_test_delete_queue";
    public static final String DELAY_QUEUE = "delay_test_queue";

    public static final String DELAY_DELETE_KEY = "delay_test_delete_key";
    public static final String DELAY_KEY = "delay_test_key";

    /**
     * 创建普通队列
     *
     * @return
     */
    @Bean
    Queue delayDeleteQueue() {
        return QueueBuilder.durable(DELAY_DELETE_QUEUE)
                .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE) // 设置死信交换机
                .withArgument("x-dead-letter-routing-key", DELAY_KEY) // 设置死信routingKey
                //.withArgument("x-message-ttl", EXPIRATION) // 设置队列的过期时间
                .build();
    }

    //交换机
    @Bean
    DirectExchange delayDeleteExchange() {
        return new DirectExchange(DELAY_DELETE_EXCHANGE);
    }

    @Bean
    Binding delayDeleteBinding(){
        return BindingBuilder.bind(delayDeleteQueue()).to(delayDeleteExchange()).with(DELAY_DELETE_KEY);
    }

    @Bean
    Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE).build();
    }

    //交换机
    @Bean
    DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE);
    }

    @Bean
    Binding delayBinding(){
        return BindingBuilder.bind(delayDeleteQueue()).to(delayDeleteExchange()).with(DELAY_DELETE_KEY);
    }










}
