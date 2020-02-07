package com.casestudy.Case3.Producer1;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.casestudy.common.OnlineRetail;
import com.casestudy.common.RetailRecord;

@SpringBootApplication
@EnableEurekaClient
public class AvroProducer {

	public static void main(String[] args) throws Exception {

		String topicName = "AvroOnlineRetail12";
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
		props.put("schema.registry.url", "http://localhost:8081");

		Producer<String, RetailRecord> producer = new KafkaProducer<>(props);
		RetailRecord cr = new RetailRecord();
		try {
			final ProducerRecord<String, RetailRecord> record = new ProducerRecord<String, RetailRecord>(
					topicName, cr);
			cr.setDescription("Test");
			cr.setQuantity("1");
			cr.setUnitPrice("2");
			producer.send(record).get();
			System.out.println("Complete");
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		} finally {
			producer.close();
		}

	}
}
