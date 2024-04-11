package com.producerconsumer.rabbitmq.config;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/10/24 7:27 AM
 */
public class RabbitMqConstants {

    public static final String PRODUCT_QUEUE = "product_queue";
    public static final String PRODUCT_EXCHANGE = "product_exchange";
    public static final String PRODUCT_ROUTING_KEY = "product_routing_key";

    public static final String DEAD_LETTER_QUEUE = "dead.letter.queue";
    public static final String DEAD_LETTER_EXCHANGE = "dead.letter.exchange";
    public static final String DEAD_LETTER_ROUTING_KEY = "dead.letter";
}
