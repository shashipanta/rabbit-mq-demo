package com.producerconsumer.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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



    // Exchange -> binding -> Queue
    @Bean
    public Queue productQueue() {
        return new Queue(PRODUCT_QUEUE);
    }

    @Bean
    public Exchange productExchange() {
        return new TopicExchange(PRODUCT_EXCHANGE);
    }

    @Bean
    public Binding productBinding() {
        return BindingBuilder
                .bind(productQueue())
                .to(productExchange())
                .with(PRODUCT_ROUTING_KEY).noargs();
    }

}
