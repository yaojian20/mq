package com.yao.mq.kafka.controller;

import com.yao.mq.kafka.service.ProducerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by yaojian on 2022/2/9 14:39
 *
 * @author
 */
@RestController
@RequestMapping("/kafka")
public class KafkaController {


    @Resource
    private ProducerService producerService;

    @GetMapping("/send")
    public void send(@RequestParam(value = "topic")String topic, @RequestParam(value = "msg")String messgae){
        if (null == topic || topic.isEmpty()){
            topic = "MyTopic";
        }
        System.out.println("message is :" + messgae);
        producerService.sendMsg(topic, messgae);
    }


}
