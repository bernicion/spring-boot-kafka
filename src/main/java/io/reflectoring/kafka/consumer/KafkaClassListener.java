package io.reflectoring.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(id = "class-level", topics = "reflectoring-3")
public class KafkaClassListener {

    @KafkaHandler
    void listen(String message) {
        log.info("KafkaHandler[String] {}", message);
    }

    @KafkaHandler(isDefault = true)
    void listenDefault(Object object) {
        log.info("KafkaHandler[Default] {}", object);
    }
}