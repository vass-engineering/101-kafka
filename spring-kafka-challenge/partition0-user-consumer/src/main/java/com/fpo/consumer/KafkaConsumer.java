package com.fpo.consumer;

import com.fpo.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${kafka.topics.customer-changed}", partitionOffsets = {
            @PartitionOffset(partition = "0", initialOffset = "0") }), groupId = "${spring.kafka.consumer.group-id}")
    void listenToPartitionWithOffset(@Payload Customer payload, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                     @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("Logger 1 [JSON] received Payload: {} from partition-{} with offset-{}", payload, partition, offset);
    }

}
