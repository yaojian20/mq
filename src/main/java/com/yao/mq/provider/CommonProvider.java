package com.yao.mq.provider;

import com.yao.mq.config.FanoutQueueConfig;
import com.yao.mq.config.RabbitQueueConfig;
import com.yao.mq.config.TopicQueueConfig;
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


}
