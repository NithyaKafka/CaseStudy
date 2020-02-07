package com.casestudy.case1;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.casestudy.common.FileReader;
import com.casestudy.common.OnlineRetail;

public class ProducerApp {

	public static void main(String[] args) throws IOException {
		runProducer();
	}

	static void runProducer() throws IOException {
		Producer<Long, OnlineRetail> producer = ProducerCreator.createProducer();
		ArrayList<OnlineRetail> retailObjList = FileReader.readXLSFile();

		for (OnlineRetail onlineRetail : retailObjList) {
			final ProducerRecord<Long, OnlineRetail> record = new ProducerRecord<Long, OnlineRetail>(
					onlineRetail.getCountry().replaceAll(" ", ""), onlineRetail);

			try {
				RecordMetadata data = producer.send(record).get();
				System.out.println("OffSet = " + data.offset() + " Partition = " + data.partition() + " Topic = "
						+ onlineRetail.getCountry());
			} catch (Exception e) {
				System.out.println("Error in sending record");
				System.out.println(e);
			}
		}
	}
}
