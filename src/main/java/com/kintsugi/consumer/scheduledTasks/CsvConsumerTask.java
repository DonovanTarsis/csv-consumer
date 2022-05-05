package com.kintsugi.consumer.scheduledTasks;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.kintsugi.consumer.models.Product;
import com.kintsugi.consumer.respositories.ProductRepository;
import com.kintsugi.consumer.services.S3Service;
import com.kintsugi.consumer.utils.ConsumerProperties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.KafkaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CsvConsumerTask {

    @Autowired
    private ProductRepository productRepository;

    @Scheduled(fixedRate = 1000)
    public void start() {
        Properties properties = ConsumerProperties.getProperties();

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {

            consumer.subscribe(Arrays.asList("upload-csv"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Nova msg => " + record.value());
                    List<Product> list = S3Service.getObject(record.value());

                    for (Product p : list) {
                        productRepository.save(p);
                    }

                }

            }
        } catch (KafkaException e) {
            System.err.println(e.getMessage());
        }

    }

}
