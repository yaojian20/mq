package com.yao.mq.kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import javax.annotation.Resource;

/**
 * Created by yaojian on 2022/2/9 10:46
 *
 * @author
 */

@Service
public class ProducerServiceImpl implements ProducerService{

    @Resource
    private KafkaTemplate kafkaTemplate;


    @Override
    public void sendMsg(String topic, String message) {

        ListenableFuture listenableFuture = kafkaTemplate.send(topic, message);
        //发送成功后回调
        SuccessCallback successCallback = new SuccessCallback() {
            @Override
            public void onSuccess(Object result) {
                System.out.println("发送成功:" + result.toString());
            }
        };
        //发送失败回调
        FailureCallback failureCallback = new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送失败");
            }
        };
        listenableFuture.addCallback(successCallback,failureCallback);


    }
}
