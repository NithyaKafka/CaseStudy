package com.casestudy.case1;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import com.casestudy.common.FileReader;
import com.casestudy.common.IKafkaConstants;
import com.casestudy.common.OnlineRetail;

public class ConsumersApp implements Runnable{
	
	public static void main(String[] args) throws IOException {
		runConsumers();
	}
	
	static void runConsumers() throws IOException {
		ArrayList<String> countryList = FileReader.getAllCountry();
		for (String string : countryList) {
			ConsumersApp app = new ConsumersApp();
			string = string.replaceAll(" ", "") ;
			Thread thread = new Thread(app,string);
			thread.start(); 
		}
		runConsumer("UnitedKingdom");
	}

	@Override
	public void run() {

		Consumer<Long, OnlineRetail> consumer = ConsumerCreator.createConsumer(Thread.currentThread().getName());

		int noMessageToFetch = 0;

		while (true) {
			final ConsumerRecords<Long, OnlineRetail> consumerRecords = consumer.poll(1000);
			if (consumerRecords.count() == 0) {
				noMessageToFetch++;
				if (noMessageToFetch > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
					break;
				else
					continue;
			}

			consumerRecords.forEach(record -> {
					System.out.println("OffSet = " + record.offset() + " Partition = " + record.partition() + " Topic = "
							+ record.value().getCountry()							
							+" InvoiceNo = "+record.value().getInvoiceNumber()
							+" StockCode = "+record.value().getStockCode()
							+" Description = "+record.value().getDescription()
							+" Quantity = "+record.value().getQuantity()
							+" InvoiceDate = "+record.value().getInvoiceDate()
							+" UnitPrice = "+record.value().getUnitPrice()
							+" CustomerID = "+record.value().getCustomerID());
					
					
			});
			consumer.commitAsync();
		}
		consumer.close();		
	}
	public static void runConsumer(String topicName) {

		Consumer<Long, OnlineRetail> consumer = ConsumerCreator.createConsumer1(topicName);

		int noMessageToFetch = 0;

		while (true) {
			final ConsumerRecords<Long, OnlineRetail> consumerRecords = consumer.poll(1000);
			if (consumerRecords.count() == 0) {
				noMessageToFetch++;
				if (noMessageToFetch > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
					break;
				else
					continue;
			}

			consumerRecords.forEach(record -> {
				System.out.println("Inside runConsumer");
					System.out.println("OffSet = " + record.offset() + " Partition = " + record.partition() + " Topic = "
							+ record.value().getCountry()							
							+" InvoiceNo = "+record.value().getInvoiceNumber()
							+" StockCode = "+record.value().getStockCode()
							+" Description = "+record.value().getDescription()
							+" Quantity = "+record.value().getQuantity()
							+" InvoiceDate = "+record.value().getInvoiceDate()
							+" UnitPrice = "+record.value().getUnitPrice()
							+" CustomerID = "+record.value().getCustomerID());
					
					
			});
			consumer.commitAsync();
		}
		consumer.close();		
	}
}
