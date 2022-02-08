package com.yao.mq.kafka.producer;

import com.yao.mq.kafka.partition.MyPartition;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.util.Properties;

/**
 * Created by yaojian on 2022/2/8 11:54
 *
 * @author
 */
public class KafkaProducer {

    private static String KAFKA_BROKER_LIST = "192.168.88.88:9092,192.168.88.89:9092,192.168.88.90:9092";

    private  final org.apache.kafka.clients.producer.KafkaProducer<Integer,String> producer;

    private final String topic;

    public KafkaProducer(String topic) {
        Properties properties = new Properties();
        properties.put("bootstrap.server", KAFKA_BROKER_LIST);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //设置分区类
        properties.put("partitioner.class", "com.yao.mq.kafka.partition.MyPartition");
        properties.put("client.id", "producerDemo");
        this.producer = new org.apache.kafka.clients.producer.KafkaProducer<Integer, String>(properties);
        this.topic = topic;
    }

    public void sendMsg(String message){
        producer.send(new ProducerRecord<Integer, String>(topic, message), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {


            }
        });
    }


    public static void main(String[] args) {
        KafkaProducer kafkaProducer = new KafkaProducer("second");

    }


}
