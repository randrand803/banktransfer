package com.bank.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import com.bank.model.TransferRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;



public class KafkaUtil {
	

	//Produce data
	public static void ProducerFunction(List<TransferRecord> list) {
        Properties props = new Properties();
        //props.put("bootstrap.servers", "192.168.237.129:9092");
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        //retries：If entry is greater than one, client will resend when it failed
        props.put("retries", 0);
        props.put("batch.size", 16384);
        //key.serializer：serialization，默认org.apache.kafka.common.serialization.StringDeserializer
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //value.serializer：值序列化，默认org.apache.kafka.common.serialization.StringDeserializer。
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        String topic = TimeUtil.TOPIC;
        for(TransferRecord item:list) {
          //producer.send(new ProducerRecord<String, String>(topic, "Message", messageStr+messageNo));
          ProducerRecord producerRecord = new ProducerRecord(topic,item.toString());
          System.out.println("产生一条消息："+item.toString());
          producer.send(producerRecord);
        }
        producer.close();
    }
	
	
	//消费数据
    public static List<String> consumerFunction() {
    	List<String> result=new ArrayList<>();
        ConsumerRecords<String, String> msgList;
        String topic=TimeUtil.TOPIC;
        String GROUPID = "groupA";
        Properties props = new Properties();
        //bootstrap.servers：kafka's address
        //props.put("bootstrap.servers", "192.168.237.129:9092");
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", GROUPID);
       //enable.auto.commit：default: true
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
       //session.timeout.ms：超时时间
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(topic));  
        int messageNo = 1;
        boolean isOver=false;
        System.out.println("---------Start consuming---------");
        try {
            for (;!isOver ; ) {
                msgList = consumer.poll(1000);
                if (null != msgList && msgList.count() > 0) {
                    for (ConsumerRecord<String, String> record : msgList) {
                         System.out.println(messageNo + "=======receive: key = " + record.key() + ", value = " + record.value() + " offset===" + record.offset());
                         result.add(record.value());
                        //Exit when consuming 50000 messages
                        if (messageNo % 50000 == 0) {
                        	System.out.println("Exited....");
                        	isOver=true;
                            break;
                        }
                        messageNo++;
                    }
                } else {
                	System.out.println("Exited....");
                	isOver=true;     
                }
              }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
        return result;
    }
    
   public static void main(String[] args) {
	   //产生数据
	   ProducerFunction(ExchangeUtil.createRecord());
	   //消费数据
	   List<String> result=consumerFunction();
	   for(String item:result) {
		   System.out.println(item);
	   }
	   
	}

}
