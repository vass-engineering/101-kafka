package com.fpo.controller;

import com.fpo.config.KafkaTopicsProperties;
import com.fpo.model.Customer;
import com.fpo.producer.KafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
@Slf4j
public class CustomerController {


    private final KafkaProducer kafkaProducer;
    private final KafkaTopicsProperties kafkaTopicsProperties;

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendCustomer(@RequestBody Customer customer) {
        log.info("Sending customer request: " + customer.toString());
        kafkaProducer.send(kafkaTopicsProperties.getTopics().getCustomerChanged(), customer);
    }
}
