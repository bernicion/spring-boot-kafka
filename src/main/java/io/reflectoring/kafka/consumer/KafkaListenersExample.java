package io.reflectoring.kafka.consumer;

import io.reflectoring.kafka.model.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaListenersExample {
    @KafkaListener(topics = "reflectoring-1")
    void listener(String data) {
        log.info(data);
    }

    @KafkaListener(topics = {"reflectoring-1", "reflectoring-2"}, groupId = "reflectoring-group-2")
    void commonListenerForMultipleTopics(String message) {
        log.info("MultipleTopicListener - {}", message);
    }

    @KafkaListener(groupId = "reflectoring-group-3",
            topicPartitions = @TopicPartition(
                    topic = "reflectoring-1",
                    partitionOffsets = {@PartitionOffset(
                            partition = "0",
                            initialOffset = "0")}))
    void listenToPartitionWithOffset(@Payload String message,
                                     @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                     @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("Received message [{}] from partition-{} with offset-{}", message, partition, offset);
    }

    @KafkaListener(topics = "reflectoring-others")
    @SendTo("reflectoring-1")
    String listenAndReply(String message) {
        log.info("ListenAndReply [{}]", message);
        return "This is a reply sent after receiving message";
    }

    @KafkaListener(topics = "reflectoring-user", groupId = "reflectoring-user", containerFactory = "userKafkaListenerContainerFactory")
    void listener(User user) {
        log.info("CustomUserListener [{}]", user);
    }
}
