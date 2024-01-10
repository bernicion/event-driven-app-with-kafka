package com.microservices.demo.twitter.to.kafka;

import com.microservices.demo.twitter.to.kafka.config.TwitterToKafkaServiceConfigData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class TwitterToKafkaServiceApplication implements CommandLineRunner {
    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Hello World!");

        log.info("App started with config: {}", twitterToKafkaServiceConfigData);
    }
}
