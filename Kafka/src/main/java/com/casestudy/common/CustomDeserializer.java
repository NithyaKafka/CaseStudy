package com.casestudy.common;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;



public class CustomDeserializer implements Deserializer<OnlineRetail> {
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	@Override
	public OnlineRetail deserialize(String topic, byte[] data) {
		ObjectMapper mapper = new ObjectMapper();
		OnlineRetail object = null;
		try {
			object = mapper.readValue(data, OnlineRetail.class);
		} catch (Exception exception) {
			System.out.println("Error in deserializing bytes " + exception);
		}
		return object;
	}

	@Override
	public void close() {
	}
}