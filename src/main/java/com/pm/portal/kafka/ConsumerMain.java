package com.pm.portal.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerMain {

	public static void main(String[] args) {
		System.out.println("....");
		// 配置信息
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.1.245:9092");
		// 必须指定消费者组  只会给一个消费者组的成员 实现负载均衡的机制
		props.put("group.id", "test3");
		props.put("key.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		// 订阅 my-topic 的消息
		consumer.subscribe(Arrays.asList("test"));
		// 到服务器中读取记录
		while (true) {
			System.out.println("....");
			ConsumerRecords<String, String> records = consumer.poll(1000);
			for (ConsumerRecord<String, String> record : records) {
				System.out.println("这是消费者A，key: " + record.key() + ", value: " + record.value());
			}
		}
	}

}
