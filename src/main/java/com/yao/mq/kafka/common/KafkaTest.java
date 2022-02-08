package com.yao.mq.kafka.common;

/**
 * Created by yaojian on 2022/1/25 10:32
 *
 * @author
 */
public class KafkaTest {

    /**
     * kafka将消息发到topic（数据集合，并不是其他mq的发布订阅模式），topic里有很多partition，数据存在partition
     * consumerGroup来实现发布订阅模式
     * 一个Topic可以认为是一类消息，每个topic将被分成多个partition(区),每个partition在存储层面是append log文件
     * 一个Topic的多个partitions,被分布在kafka集群中的多个server上;每个server(kafka实例)负责partitions中消息的读写操作;
     * 此外kafka还可以配置partitions需要备份的个数(replicas),每个partition将会被备份到多台机器上,以提高可用性.
     *
     * 　　基于replicated方案,那么就意味着需要对多个备份进行调度;每个partition都有一个server为"leader";leader负责所有的读写操作,
     * 如果leader失效,那么将会有其他follower来接管(成为新的leader);follower只是单调的和leader跟进,同步消息即可..
     * 由此可见作为leader的server承载了全部的请求压力,
     * 因此从集群的整体考虑,有多少个partitions就意味着有多少个"leader",kafka会将"leader"均衡的分散在每个实例上,来确保整体的性能稳定。
     */

    /**
     * 消息由key【可选】和value组成，都是byte数组，key可以为null，key可以决定消息去哪个分组，不知道默认为null
     * 消息可以批量发送
     */

    /**
     * topic和partition
     *
     * topic是逻辑上的概念，partition是物理上的概念
     * partition里是顺序写的，而且不跨分区
     * kafka高吞吐量的因素
     * 1.顺序写的方式存储数据
     * 2.批量发送，异步的时候允许批量发送
     * 3.零拷贝（不需要进行CPU拷贝）
     */


    /**
     * 日志策略
     * 日志保留策略跟日志压缩策略
     * 日志保留策略根据保留时间跟日志大小决定保留策略
     * 日志压缩策略对相同key的value进行合并
     */


    /**
     * kafka消息的可靠性
     * 生产者发送消息到broker上有三种确认模式
     * 1.acks = 0 producer不会等待broker发送ack。可能会丢失也有可能会重发
     * 2.acks = 1 当leader接收到消息会发ack，丢会重发，丢的概率很小
     * 3.acks = -1 当所有的follower同步消息后会发ack，丢失消息的可能性很低
     * 消息存储可靠性，可自定义partition规则，消息会水平地发往partition
     */

    /**
     * 副本机制
     * sh kafka-topics.sh --create -zookeeper 192.168.88.88:2181 --replication-factor 1 --partitions 3 --topic second
     * replication-factor表示副本数
     * follower会从leader上复制数据，leader挂掉之后会从follower中选出新的leader，从ISR中选出来
     * ISR -> 副本同步队列 维护的是follower节点（有些follower节点没有资格存在ISR中）
     * 有资格的follower节点
     * 1.副本的所有节点要和zookeeper保持连接
     * 2.副本的最后一条消息的offset和leader最后一条消息的offset的差值不能超过规定的阈值，这个阈值可以设置（replica.lag.max.message）
     *
     * leader节点负责写入数据，follower节点会从leader节点上复制数据
     * 消费者只能消费hw标记的消息及之前的消息（只有所有follower节点都同步了该消息，消费者才能消费该消息），所以副本数多了会影响性能
     * 同步会导致如果follower挂掉之后，无法消费该消息，因为follower节点此时还没同步，
     * 所以通过ISR将offset差的太多的follower踢出ISR，这个时候就不需要关挂掉的follower节点，这样就可以消费了
     *
     */

    /**
     * HW和LEO
     * High water 表示在ISR中所有该offset的数据都被同步了，此时该消息可以被消费
     * 没有都被同步的数据标记为LEO（log end offset）,
     *
     */


    /**
     * kafka的分区分配策略
     * 同一个分区的消息只能由同一个分组的消费者消费
     * 同一个consumers group里的消费者怎么分配分区
     * consumers的rebalance策略
     * 默认是范围策略，还有一个是轮询策略
     */



}
