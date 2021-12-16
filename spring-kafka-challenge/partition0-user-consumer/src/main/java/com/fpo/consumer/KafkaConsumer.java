package com.fpo.consumer;

import com.fpo.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    void listenToPartitionWithOffset(@Payload Customer payload, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                    @Header(KafkaHeaders.OFFSET) int offset) {
    }

}
