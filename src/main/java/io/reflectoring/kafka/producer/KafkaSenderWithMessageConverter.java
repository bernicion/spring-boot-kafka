package io.reflectoring.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaSenderWithMessageConverter {
    private final KafkaTemplate<String, ?> kafkaTemplate;

    public void sendMessageWithConverter(Message<?> user) {
        log.info("Sending With Message Converter : {}", user);
        log.info("--------------------------------");
        kafkaTemplate.send(user);
    }
}
