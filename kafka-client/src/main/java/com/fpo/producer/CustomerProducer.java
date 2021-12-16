package com.fpo.producer;

import com.fpo.model.Customer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.Future;

public class CustomerProducer {

    private static final Logger logger = LoggerFactory.getLogger(CustomerProducer.class);
    private static CustomerProducer instance = null;
    private static KafkaProducer<String, Customer> producer;

    private CustomerProducer(Properties properties) {
        producer = new KafkaProducer <>(properties);
    }

    public static CustomerProducer getInstance() {
        if (instance == null) {
            instance = new CustomerProducer(getDefaultProperties());
        }
        return instance;
    }



    private static Properties getDefaultProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomerSerializer.class.getName());

        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, String.valueOf(Integer.MAX_VALUE));
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");

        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024)); // 32 kb

        return properties;

    }

    public Future<RecordMetadata> sendRecord(String topic, String key, Customer value) {

        ProducerRecord<String, Customer> record = new ProducerRecord <>(topic, key, value);

        return producer.send(record, handleResult());
    }

    private Callback handleResult() {
        return (metadata, exception) -> {
            if (exception == null) {
                logger.info("Message sent: {}", metadata.toString());
            } else {
                logger.error("Message failed with exception: {}", exception.getMessage());
            }
        };
    }

    public void close() {
        producer.flush();
        producer.close();
    }
}
