package com.yao.mq.kafka.producer;

import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import scala.Int;

import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

/**
 * Created by yaojian on 2022/2/8 14:11
 *
 * @author
 */
public class Consumer extends ShutdownableThread {
    /**
     * 消息的消费原理
     * 之前offset全部维护在zookeeper上，zookeeper操作频繁（0.8之前）
     * 新版的offset保存在kafka本地的topic里的
     */




    //high level consumer
    //low level consumer

    private static String KAFKA_BROKER_LIST = "192.168.88.88:9092,192.168.88.89:9092,192.168.88.90:9092";

    private KafkaConsumer<Integer, String> consumer;

    //消息确认的几种机制
    //1.自动提交         //是否自动提交消息：offset
    //        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
    //        //自动提交的时间间隔
    //        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

    //2.手动提交（同步/异步）

    public Consumer() {
        super("kafkaConsumerTest", false);
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,KAFKA_BROKER_LIST);
        //group id消息所属的分组id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoGroup1");
        //是否自动提交消息：offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        //自动提交的时间间隔
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        //设置使用最开始的offset偏移量为当前group id的最早消息
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        //设置心跳时间
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        //对key设置反序列化对象
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<Integer, String>(properties);
        //消费指定分区的消息
        //TopicPartition topicPartition = new TopicPartition("second", 0);
        //consumer.assign(Arrays.asList(topicPartition));
    }

    @Override
    public void doWork() {

        //second是消费的topic
        consumer.subscribe(Collections.singletonList("second"));
        ConsumerRecords<Integer, String> records = consumer.poll(1000);
        for (ConsumerRecord record : records){
            System.out.println(record.key() + "-》" + record.value() + record.partition());
        }
        //获得offset存在消息分区
        System.out.println(Math.abs("DemoGroup1".hashCode())%50);

    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.start();
    }



}
