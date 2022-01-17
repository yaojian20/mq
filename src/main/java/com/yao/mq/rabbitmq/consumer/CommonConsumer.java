package com.yao.mq.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.yao.mq.rabbitmq.config.FanoutQueueConfig;
import com.yao.mq.rabbitmq.config.RabbitDelayQueueConfig;
import com.yao.mq.rabbitmq.config.RabbitQueueConfig;
import com.yao.mq.rabbitmq.config.TopicQueueConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by yaojian on 2022/1/13 20:04
 *
 * @author
 */
@Component
public class CommonConsumer {

    //消费端监听COMMON_QUEUE消息队列
    @RabbitListener(queues= RabbitQueueConfig.COMMON_QUEUE)
    public void consumerMessage(String message, @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag, Channel channel){

        System.out.println("正在消费消息：" + message.toString());
        //确认消费
        try {
            //因为设置了手动确认消费，所以这边必须要手动回复已正确消费，不然重启之后会再消费一次
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RabbitListener(queues= TopicQueueConfig.TOPIC_QUEUE_STUDENT)
    public void consumerStudentMessage(String message, @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag, Channel channel){

        System.out.println("TOPIC_QUEUE_STUDENT正在消费消息：" + message.toString());
        //确认消费
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RabbitListener(queues= TopicQueueConfig.TOPIC_QUEUE_TEACHER)
    public void consumerTeacherMessage(String message, @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag, Channel channel){

        System.out.println("TOPIC_QUEUE_TEACHER正在消费消息：" + message.toString());
        //确认消费
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RabbitListener(queues= FanoutQueueConfig.FANOUT_A_QUEUE)
    public void consumerFanoutAMessage(String message, @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag, Channel channel){

        System.out.println("FANOUT_A_QUEUE正在消费消息：" + message.toString());
        //确认消费
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RabbitListener(queues= FanoutQueueConfig.FANOUT_B_QUEUE)
    public void consumerFanoutBMessage(String message, @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag, Channel channel){

        System.out.println("FANOUT_B_QUEUE正在消费消息：" + message.toString());
        //确认消费
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RabbitListener(queues= FanoutQueueConfig.FANOUT_C_QUEUE)
    public void consumerFanoutCMessage(String message, @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag, Channel channel){

        System.out.println("FANOUT_C_QUEUE正在消费消息：" + message.toString());
        //确认消费
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RabbitListener(queues= RabbitDelayQueueConfig.DELAY_QUEUE)
    public void consumerDelayMessage(String message, @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag, Channel channel){

        System.out.println("DELAY_QUEUE正在消费消息：" + message.toString());
        //确认消费
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
