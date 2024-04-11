package com.producerconsumer.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/7/24 6:21 AM
 */

@Configuration
public class RabbitMqConfig {

    private static final String PRODUCT_QUEUE = "product_queue";
    private static final String PRODUCT_EXCHANGE = "product_exchange";
    private static final String PRODUCT_ROUTING_KEY = "product_routing_key";

    private static final String DEAD_LETTER_QUEUE = "dead.letter.queue";
    private static final String DEAD_LETTER_EXCHANGE = "dead.letter.exchange";
    private static final String DEAD_LETTER_ROUTING_KEY = "dead.letter";

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DEAD_LETTER_QUEUE);
    }

    @Bean
    public Exchange deadLetterExchange() {
        return new TopicExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DEAD_LETTER_ROUTING_KEY).noargs();
    }

    // Exchange -> binding -> Queue
    @Bean
    public Queue productQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY);
        args.put("x-message-ttl", 4000);
        return new Queue(PRODUCT_QUEUE, true, false, false, args);
    }

    @Bean
    public Exchange productExchange() {
        return new TopicExchange(PRODUCT_EXCHANGE);
    }

    // Main Queue
    @Bean
    public Binding productBinding() {
//        Map<String, Object> args = new HashMap<>();
//        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
//        args.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY);
//        args.put("x-message-ttl", 4000);
//        Queue productQueue = new Queue(PRODUCT_QUEUE, true, false, false, args);
        return BindingBuilder
                .bind(productQueue())
                .to(productExchange())
                .with(PRODUCT_ROUTING_KEY).noargs();
    }

}
