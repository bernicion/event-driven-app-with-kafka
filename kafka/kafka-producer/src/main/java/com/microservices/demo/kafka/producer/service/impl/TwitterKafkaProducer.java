package com.microservices.demo.kafka.producer.service.impl;


import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
import com.microservices.demo.kafka.producer.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TwitterKafkaProducer implements KafkaProducer<Long, TwitterAvroModel> {

    private final KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate;

    @Override
    public void send(String topicName, Long key, TwitterAvroModel message) {
        CompletableFuture<SendResult<Long, TwitterAvroModel>> kafkaResultFuture =
                kafkaTemplate.send(topicName, key, message);
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            kafkaTemplate.destroy();
        }
    }

}
