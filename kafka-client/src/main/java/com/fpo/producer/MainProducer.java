package com.fpo.producer;

import com.fpo.model.Customer;
import com.fpo.model.CustomerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class MainProducer {

    private static final Logger logger = LoggerFactory.getLogger(MainProducer.class);
    private static String[] names = { "Kr", "Ca", "Ra", "Mrok", "Cru",
            "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
            "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
            "Mar", "Luk" };
    private static final String topicName="customer.change";
    private static Random rand = new Random();

    public static void main(String[] args)  {
        int N = 500;
        CustomerProducer producer = CustomerProducer.getInstance();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Gracefully shutting down");
            producer.close();
        }));

        String keyPrefix = "customer";
        Customer customer = null;
        CustomerType type = null;
        for (int i = 0; i < N; i++) {
            if (i%2==0) type = CustomerType.STANDARD;
            else  type = CustomerType.VIP;
            customer = new Customer(names[rand.nextInt(names.length)],type);
            producer.sendRecord(topicName, keyPrefix + i, customer);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
