package com.devesta.wikimedia.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WikiMediaConsumer {

    private final String TOPIC_NAME = "wikimedia-stream";

    @KafkaListener(topics = TOPIC_NAME, groupId = "myGroup")
    public void consumer(String message) {
        log.info(String.format("Consuming %s from %s", message, TOPIC_NAME));
    }


}
