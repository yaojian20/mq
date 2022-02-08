package com.yao.mq.kafka.partition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by yaojian on 2022/2/8 17:54
 *
 * @author
 */
//自己实现分区
public class MyPartition implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        List<PartitionInfo> partitionInfoList = cluster.partitionsForTopic(topic);
        int numPart = partitionInfoList.size();
        int hashCode = key.hashCode();
        return Math.abs(hashCode%numPart);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
