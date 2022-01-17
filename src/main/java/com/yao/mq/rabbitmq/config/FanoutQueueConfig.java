package com.yao.mq.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yaojian on 2022/1/14 11:35
 *
 * @author
 */
//扇形交换机（发送消息给与该交换机绑定的所有消息队列，不需要binding key跟routing key）
@Configuration
public class FanoutQueueConfig {

    public static final String FANOUT_EXCHANGE = "fanout_exchange";

    public static final String FANOUT_A_QUEUE = "fanout_a";

    public static final String FANOUT_B_QUEUE = "fanout_b";

    public static final String FANOUT_C_QUEUE = "fanout_c";

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Queue fanoutAQueue(){
        return QueueBuilder.durable(FANOUT_A_QUEUE).build();
    }
    @Bean
    public Queue fanoutBQueue(){
        return QueueBuilder.durable(FANOUT_B_QUEUE).build();
    }
    @Bean
    public Queue fanoutCQueue(){
        return QueueBuilder.durable(FANOUT_C_QUEUE).build();
    }

    @Bean
    public Binding fanoutBindingA(){
        return BindingBuilder.bind(fanoutAQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBindingB(){
        return BindingBuilder.bind(fanoutBQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBindingC(){
        return BindingBuilder.bind(fanoutCQueue()).to(fanoutExchange());
    }




}
