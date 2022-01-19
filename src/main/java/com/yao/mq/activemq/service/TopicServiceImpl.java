package com.yao.mq.activemq.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * Created by yaojian on 2022/1/19 15:25
 *
 * @author
 */
@Service
public class TopicServiceImpl {


    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Resource
    private Queue queue;

    @Resource
    private Topic topic;

    public void sendMessageToQueue(String message){
        jmsMessagingTemplate.convertAndSend(queue, message);
        System.out.println("发送消息给queue成功，消息为：" + message);
    }

    public void sendMessageToTopic(String message){
        jmsMessagingTemplate.convertAndSend(topic, message);
        System.out.println("发送消息给topic成功，消息为：" + message);
    }


    @JmsListener(destination = "out.queue")
    public void consumerMsg(String msg){
        System.out.println(msg);
    }



}
