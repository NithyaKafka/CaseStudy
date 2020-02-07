package com.casestudy.Case3.Consumer1;

import java.util.*;
import org.apache.kafka.clients.consumer.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.casestudy.common.IKafkaConstants;
import com.casestudy.common.OnlineRetail;
import com.casestudy.common.RetailRecord;

@SpringBootApplication
@EnableEurekaClient
public class AvroConsumer{    
    
    public static void main(String[] args) throws Exception{

        String topicName = "AvroOnlineRetail12";
            
        String groupName = "RG";
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupName);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        props.put("schema.registry.url", "http://localhost:8081");
        props.put("specific.avro.reader", "true");
        int noMessageToFetch = 0;
        KafkaConsumer<String, RetailRecord> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topicName));
        try{
        	while (true) {
    			final ConsumerRecords<String, RetailRecord> consumerRecords = consumer.poll(100);
    			if (consumerRecords.count() == 0) {
    				noMessageToFetch++;
    				if (noMessageToFetch > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
    					break;
    				else
    					continue;
    			}
    			System.out.println("consumerRecords"+consumerRecords);
    			Iterator t = consumerRecords.iterator();
    			while(t.hasNext()) {
    				System.out.println("Result"+t.next());
    			}
/*				for (int i = 0; i < consumerRecords.count(); i++) {
					System.out.println("record" + consumerRecords);
					System.out.println("Session id=" + record.value().getDescription() + " Channel="
							+ record.value().getQuantity() + " Referrer=" + record.value().getUnitPrice());
				}*/
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
            finally{
                consumer.close();
            }
    }
    
}
