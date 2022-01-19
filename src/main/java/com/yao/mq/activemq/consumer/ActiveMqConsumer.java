package com.yao.mq.activemq.consumer;

import com.yao.mq.activemq.config.ActiveMqConfig;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * Created by yaojian on 2022/1/19 15:39
 *
 * @author
 */
@Component
public class ActiveMqConsumer {

    @JmsListener(destination = ActiveMqConfig.QUEUE_NAME, containerFactory = "jmsListenerContainerQueue")
    //会消息发到out.queue
    @SendTo("out.queue")
    public String receive(String text){
        System.out.println("QueueListener: consumer-a 收到一条信息: " + text);
        return "consumer-a received : " + text;
    }



}
