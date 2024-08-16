package com.devesta.wikimedia.producer.stream;


import com.devesta.wikimedia.producer.producer.WikiMediaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class WikiMediaStreamReceiver {

    private final WebClient client;
    private final WikiMediaProducer producer;

    public WikiMediaStreamReceiver(WikiMediaProducer producer) {
        this.client = WebClient
                .builder()
                .baseUrl("https://stream.wikimedia.org/v2")
                .build();
        this.producer = producer;
    }

    public void consumeStreamAndPublish() {
        Flux<String> payloadFlux = client.get()
                .uri("/stream/recentchange")
                .retrieve()
                .bodyToFlux(String.class);

        payloadFlux.subscribe(
                producer::sendMessage,
                error -> log.error("Error occurred while consuming the stream", error),
                () -> log.info("Stream completed")
        );

    }

}
