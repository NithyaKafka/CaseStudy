package com.casestudy.case2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.casestudy.common.FileReader;
import com.casestudy.common.OnlineRetail;

@RestController
@RequestMapping("/RetailerDetails")
public class ProducerService {

	@Autowired
	WebClient.Builder webClientBuilder;
	@Autowired
	private KafkaTemplate<String, OnlineRetail> template;

	@GetMapping(path = "/Send")
	public void sender() throws IOException, InterruptedException, ExecutionException {

		ArrayList<OnlineRetail> retailObjList = FileReader.readXLSFile();

		for (OnlineRetail onlineRetail : retailObjList) {
			SendResult data = this.template.send(onlineRetail.getCountry().replaceAll(" ", ""), onlineRetail).get();
			System.out.println("OffSet = " + data.getRecordMetadata().offset() + " Partition = " + data.getRecordMetadata().partition() + " Topic = "
					+ onlineRetail.getCountry());
		}

	}

}
