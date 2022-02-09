package com.yao.mq.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yaojian on 2022/2/9 11:42
 *
 * @author
 */
@Component
public class ConsumerGroup {

    @KafkaListener(topics = {"GroupQueue"},containerFactory = "kafkaListenerContainerFactory")
    public void listen(List<ConsumerRecord> records , Acknowledgment ack) {
        try {
            for (ConsumerRecord record : records) {
                System.out.println(String.format("offset = %d, key = %s, value = %s%n \n", record.offset(), record.key(), record.value()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //手动提交偏移量
            ack.acknowledge();
        }
    }

}
