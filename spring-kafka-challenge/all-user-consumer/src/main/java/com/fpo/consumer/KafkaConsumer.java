package com.fpo.consumer;

import com.fpo.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    public void listenToCustomerChange(ConsumerRecord<String, Customer> cr, @Payload Customer payload) {
    }

}
