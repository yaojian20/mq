package com.yao.mq.controller;

import com.yao.mq.provider.CommonProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by yaojian on 2022/1/13 20:05
 *
 * @author
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Resource
    private CommonProvider commonProvider;


    @GetMapping("/send")
    public void send(@RequestParam("message")String message,String queueType){
        commonProvider.sendCommonMessage(message);
    }

    @GetMapping("/topic/send")
    public void sendTopic(@RequestParam("message")String message,Integer queueType){
        if (queueType == null){
            commonProvider.sendTeacherMessage(message);
            return;
        }
        switch (queueType){
            case 1 :
                //发送给student时,teacher的queue也会收到消息
                //因为teacherQueue交换机绑定的biding key 为"topic.*"，所以发送消息时候的routing key以top.开头的他都会收到
                commonProvider.sendStudentMessage(message);
                break;
            case 2 :
                commonProvider.sendTeacherMessage(message);
                break;
        }

    }

    @GetMapping("/fanout/send")
    public void sendFanout(@RequestParam("message")String message){
        commonProvider.sendFanoutMessage(message);
    }



}
