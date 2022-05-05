package com.kintsugi.consumer;

import com.kintsugi.consumer.services.KafkaService;
import com.kintsugi.consumer.services.S3Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);

		KafkaService.consumer();
		// S3Service.getObject();
	}

}
