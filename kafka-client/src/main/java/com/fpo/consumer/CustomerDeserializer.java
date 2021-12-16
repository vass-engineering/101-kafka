package com.fpo.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpo.model.Customer;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public final class CustomerDeserializer implements Deserializer<Customer> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Customer deserialize(String s, byte[] bytes) {
        final var value = new String(bytes);
        try {
            final var payload = objectMapper.readValue(value, Customer.class);
            return payload;
        } catch (Throwable e) {
            throw new SerializationException("Error when deserializing byte[] to Customer");
        }
    }
}
