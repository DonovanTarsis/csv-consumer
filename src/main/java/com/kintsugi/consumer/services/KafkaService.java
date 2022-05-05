package com.kintsugi.consumer.services;

import java.util.Properties;

import com.kintsugi.consumer.utils.ProducerProperties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.springframework.stereotype.Component;

@Component
public class KafkaService {

    public static void producer() {
        Properties properties = ProducerProperties.getProperties();

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {

            ProducerRecord<String, String> record = new ProducerRecord<String, String>("upload-csv", "ok");
            producer.send(record);

        } catch (KafkaException e) {
            System.err.println(e.getMessage());
        }
    }

}
