package com.casestudy.case2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.client.RestTemplate;

import com.casestudy.common.OnlineRetail;

@SpringBootApplication
@EnableEurekaClient
public class ConsumerService {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerService.class, args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@KafkaListener(id = "countryGroup", topics = "UnitedKingdom")
	public void listener(OnlineRetail record) {
		printRecord(record);
	}

	@KafkaListener(id = "countryGroup1", topics = "France",clientIdPrefix = "1")
	public void listener1(OnlineRetail record) {
		printRecord(record);
	}

	public void printRecord(OnlineRetail record) {
		System.out.println(" Topic = " + record.getCountry() + " InvoiceNo = " + record.getInvoiceNumber()
				+ " StockCode = " + record.getStockCode() + " Description = " + record.getDescription() + " Quantity = "
				+ record.getQuantity() + " InvoiceDate = " + record.getInvoiceDate() + " UnitPrice = "
				+ record.getUnitPrice() + " CustomerID = " + record.getCustomerID());
	}

}
