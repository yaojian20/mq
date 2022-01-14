package com.yao.mq.config;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

/**
 * Created by yaojian on 2022/1/14 17:47
 *
 * @author
 */
public class ExpirationMessagePostProcessor implements MessagePostProcessor {

    private int expireMills;

    public ExpirationMessagePostProcessor(int expireMills) {
        this.expireMills = expireMills;
    }

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().setExpiration(expireMills+"");
        return message;
    }
}
