package com.bank;

import com.bank.util.ExchangeUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void testUSDToCNY() {
        double usd = Double.valueOf(ExchangeUtil.getExchange("USD", "CNY"));

        if (usd > 0) {
            System.out.println("successful test");
        }
    }

    @Test
    void testEURToCNY() {
        double eur = Double.valueOf(ExchangeUtil.getExchange("EUR", "CNY"));

        if (eur > 0) {
            System.out.println("successful test");
        }
    }

    @Test
    void testProducer() {
        Properties prop = new Properties();

        prop.put("bootstrap.servers", "localhost:9092");
        prop.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        prop.put("acks", "all");
        prop.put("retries", 0);
        prop.put("batch.size", 16384);
        prop.put("linger.ms", 1);
        prop.put("buffer.memory", 33554432);
        String topic = "hello";
        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);
        producer.send(new ProducerRecord<String, String>(topic, Integer.toString(2), "hello kafka3"));
        producer.close();
    }

//    @Test
//    void testConsumer() {
//        Properties prop = new Properties();
//
//        prop.put("bootstrap.servers", "localhost:9092");
//        prop.put("key.deserializer",
//                "org.apache.kafka.common.serialization.StringDeserializer");
//
//        prop.put("value.deserializer",
//                "org.apache.kafka.common.serialization.StringDeserializer");
//        prop.put("group.id", "con-1");
//        prop.put("auto.offset.reset", "latest");
//        //自动提交偏移量
//        prop.put("auto.commit.intervals.ms", "true");
//        //自动提交时间
//        prop.put("auto.commit.interval.ms", "1000");
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(prop);
//        ArrayList<String> topics = new ArrayList<>();
//        //可以订阅多个消息
//        topics.add("hello");
//        consumer.subscribe(topics);
//        while (true) {
//            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofSeconds(20));
//            if(poll.isEmpty()){
//                break;
//            }
//            for (ConsumerRecord<String, String> consumerRecord : poll) {
//                System.out.println(consumerRecord);
//            }
//        }
//    }

}
