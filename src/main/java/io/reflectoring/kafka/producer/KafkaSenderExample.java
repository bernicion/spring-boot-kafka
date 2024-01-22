package io.reflectoring.kafka.producer;

import io.reflectoring.kafka.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaSenderExample {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RoutingKafkaTemplate routingKafkaTemplate;
    private final KafkaTemplate<String, User> userKafkaTemplate;

    public void sendMessage(String message, String topicName) {
        kafkaTemplate.send(topicName, message);
    }

    public void sendMessageWithCallback(String message, String topicName) {
        log.info("Sending : {}", message);
        log.info("---------------------------------");

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Success Callback: [{}] delivered with offset -{}", message,
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("Failure Callback: Unable to deliver message [{}]. {}", message, ex.getMessage());
            }
        });
    }

    public void sendCustomMessage(User user, String topicName) {
        log.info("Sending Json Serializer : {}", user);
        log.info("--------------------------------");
        userKafkaTemplate.send(topicName, user);
    }

    public void sendWithRoutingTemplate(String message, String topicName) {
        log.info("Sending : {}", message);
        log.info("--------------------------------");
        routingKafkaTemplate.send(topicName, message.getBytes());
    }
}
