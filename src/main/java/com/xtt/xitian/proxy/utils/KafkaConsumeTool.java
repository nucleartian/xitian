package com.xtt.xitian.proxy.utils;

import com.alibaba.fastjson.JSONObject;
import com.xtt.xitian.model.Light;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Properties;

public class KafkaConsumeTool {
    private static final KafkaConsumeTool instance = new KafkaConsumeTool();

    public static Light getLight() {
        return deque.poll();
    }

    private static final Deque<Light> deque = new LinkedList<>();

    private KafkaConsumeTool() {
        init();
    }

    private void init() {
        new ConsumerThread().start();
    }


    public static KafkaConsumeTool getInstance() {
//        if(consumerThread.isAlive())
        return instance;
    }

    private static class ConsumerThread extends Thread{
        @Override
        public void run(){
            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"172.17.0.1"+":9092");
            // group.id，指定了消费者所属群组
            props.put("group.id", "test");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

            KafkaConsumer<String, String> consumer =
                    new KafkaConsumer<String, String>(props);
            consumer.subscribe(Collections.singletonList("test2151"));
            try {
                while (true) {
                    // 100 是超时时间（ms），在该时间内 poll 会等待服务器返回数据
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(5000));

                    // poll 返回一个记录列表。
                    // 每条记录都包含了记录所属主题的信息、记录所在分区的信息、记录在分区里的偏移量，以及记录的键值对。
                    for (ConsumerRecord<String, String> record : records) {
//                    log.debug("topic=%s, partition=%s, offset=%d, customer=%s, country=%s",
//                            record.topic(), record.partition(), record.offset(),
//                            record.key(), record.value());

                        Light light = JSONObject.parseObject(record.value(), Light.class);
                        deque.offer(light);
                    }
                }
            } finally {
                // 关闭消费者,网络连接和 socket 也会随之关闭，并立即触发一次再均衡
                consumer.close();
            }
        }
    }
}
