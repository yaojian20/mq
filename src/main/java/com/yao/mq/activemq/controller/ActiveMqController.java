package com.yao.mq.activemq.controller;

import com.yao.mq.activemq.service.TopicServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by yaojian on 2022/1/19 15:53
 *
 * @author
 */
@Controller
@RequestMapping("/active")
public class ActiveMqController {


    @Resource
    private TopicServiceImpl topicService;


    @RequestMapping("/sendQueue")
    public void sendQueue(String message,Integer count){
        if (message == null) message = "默认消息啦啦啦啦啦";
        if (count == null) count = 5;
        for (int i = 0; i < count ; i++){
            topicService.sendMessageToQueue(message + i);
        }
    }

}
