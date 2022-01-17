package com.yao.mq.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yaojian on 2022/1/14 9:40
 *
 * @author
 */
//主题交换机
//binding key跟routing key满足某种格式即可
@Configuration
public class TopicQueueConfig {

    public static final String TOPIC_EXCHANGE = "topic_exchange";
    public static final String TOPIC_QUEUE_STUDENT = "topic_student";
    public static final String TOPIC_QUEUE_TEACHER = "topic_teacher";
    public static final String TOPIC_STUDENT_KEY = "topic.student";
    public static final String TOPIC_TEACHER_KEY = "topic.teacher";


    @Bean
    //主题交换机
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Queue studentQueue(){
        return QueueBuilder.durable(TOPIC_QUEUE_STUDENT).build();
    }

    @Bean
    public Queue teacherQueue(){
        return QueueBuilder.durable(TOPIC_QUEUE_TEACHER).build();
    }

    @Bean
    public Binding studentBinding(){
        return BindingBuilder.bind(studentQueue()).to(topicExchange()).with(TOPIC_STUDENT_KEY);
    }

    @Bean
    //主题交换机如果没有出现*跟#的话，其实就等同于DirectExchange
    public Binding teacherBinding(){
        return BindingBuilder.bind(teacherQueue()).to(topicExchange()).with("topic.*");
    }


}
