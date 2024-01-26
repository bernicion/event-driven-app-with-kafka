package com.microservices.demo.kafka.producer.service.impl;


import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
import com.microservices.demo.kafka.producer.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwitterKafkaProducer implements KafkaProducer<Long, TwitterAvroModel> {

    private final KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate;

    @Override
    public void send(String topicName, Long key, TwitterAvroModel message) {
        log.info("Sending message {} to topic {} with key {}", message, topicName, key);
        CompletableFuture<SendResult<Long, TwitterAvroModel>> kafkaResultFuture =
                kafkaTemplate.send(topicName, key, message);

        kafkaResultFuture
                .thenAccept(result -> {
                    RecordMetadata metadata = result.getRecordMetadata();
                    log.debug("Received new metadata. Topic: {}; Partition: {}; Offset: {} TimeStamp:{} at time {}",
                            metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp(), System.nanoTime());
                })
                .exceptionally(throwable -> {
                    log.error("Error while sending message {} to topic {}", message.toString(), topicName);
                    return null;
                });
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing Kafka producer!");
            kafkaTemplate.destroy();
        }
    }

}
