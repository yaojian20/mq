package com.yao.mq.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yaojian on 2022/1/13 17:41
 *
 * @author
 */
//直连交换机（binding key跟routing key要一致才可以）
@Configuration
public class RabbitQueueConfig {

    /**
     * Broker：消息队列服务进程，包括Exchange和Queue
     * Exchange:Exchange需要按某种过滤条件将消息路由转发给消息队列
     * Queue：消息队列，存储消息的队列，消息到达队列并转发给指定的
     */

    /**
     * 生产者发送消息流程：
     *
     * 1、生产者和Broker建立TCP连接。
     *
     * 2、生产者和Broker建立通道。
     *
     * 3、生产者通过通道消息发送给Broker，由Exchange将消息进行转发。
     *
     * 4、Exchange将消息转发到指定的Queue（队列）
     *
     * 消费者接收消息流程：
     *
     * 1、消费者和Broker建立TCP连接
     *
     * 2、消费者和Broker建立通道
     *
     * 3、消费者监听指定的Queue（队列）
     *
     * 4、当有消息到达Queue时Broker默认将消息推送给消费者。
     *
     * 5、消费者接收到消息。
     *
     * 6、ack回复
     */

    //普通交换机
    public static final String COMMON_EXCHANGE = "common_test_exchange";
    //普通队列名
    public static final String COMMON_QUEUE = "common_test_queue";
    //普通路由键(绑定交换机跟消息队列)
    public static final String COMMON_KEY = "common_test_key";

    //普通交换机
    @Bean
    public DirectExchange commonExchange(){
        return new DirectExchange(COMMON_EXCHANGE);
    }

    @Bean
    public Queue commonQueue(){
        //该队列设为持久队列，即使重启消息还在
        //return new Queue(COMMON_QUEUE, true);
        //这种方式也可以创建持久化消息队列
        return QueueBuilder.durable(COMMON_QUEUE).build();
    }

    //通过key绑定消息队列跟交换机
    @Bean
    public Binding commongBinding(){
        return BindingBuilder.bind(commonQueue()).to(commonExchange()).with(COMMON_KEY);
    }







}
