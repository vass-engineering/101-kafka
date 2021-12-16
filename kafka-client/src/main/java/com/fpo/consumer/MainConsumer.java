package com.fpo.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class MainConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MainConsumer.class);
    private static final String topicName="customer.change";
    private static Random rand = new Random();

    public static void main(String[] args)  {
        CustomerConsumer consumer = new CustomerConsumer();
        consumer.startListening(topicName);

        try {
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        consumer.stopConsumer();
        consumer.closeExecutor();

        logger.info("Main exits");
    }

}
