package com.yao.mq.provider;

import com.yao.mq.config.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by yaojian on 2022/1/13 19:55
 *
 * @author
 */
@Component
public class CommonProvider {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendCommonMessage(String message){
        //发送消息到消息队列
        rabbitTemplate.convertAndSend(RabbitQueueConfig.COMMON_EXCHANGE,RabbitQueueConfig.COMMON_KEY,message);
    }

    public void sendStudentMessage(String message){
        //发送消息到消息队列
        rabbitTemplate.convertAndSend(TopicQueueConfig.TOPIC_EXCHANGE,TopicQueueConfig.TOPIC_STUDENT_KEY,message);
    }

    public void sendTeacherMessage(String message){
        //发送消息到消息队列
        rabbitTemplate.convertAndSend(TopicQueueConfig.TOPIC_EXCHANGE,TopicQueueConfig.TOPIC_TEACHER_KEY,message);
    }

    public void sendFanoutMessage(String message){
        //发送消息到消息队列
        rabbitTemplate.convertAndSend(FanoutQueueConfig.FANOUT_EXCHANGE,null,message);
    }

    public void sendDelayMessage(String message){
        //发送消息到消息队列
        rabbitTemplate.convertAndSend(RabbitDelayQueueConfig.DELAY_DELETE_EXCHANGE,RabbitDelayQueueConfig.DELAY_DELETE_KEY,message,new ExpirationMessagePostProcessor(10*1000));
    }


}
