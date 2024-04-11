package com.producerconsumer.rabbitmq.publisher;

import com.producerconsumer.rabbitmq.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/7/24 6:44 AM
 */

@Service
@RequiredArgsConstructor
public class ProductPublisher {

    private final RabbitTemplate rabbitTemplate;

    private static final String PRODUCT_ROUTING_KEY = "product_routing_key";
    private static final String PRODUCT_EXCHANGE = "product_exchange";

    public void createProduct(Product product) {
        rabbitTemplate.convertAndSend(PRODUCT_EXCHANGE, PRODUCT_ROUTING_KEY, product);
    }

    public void updateProduct(Product product) {
        rabbitTemplate.convertAndSend(PRODUCT_EXCHANGE, PRODUCT_ROUTING_KEY, product);
    }

    public void invalidExchange(Object o) {
        rabbitTemplate.convertAndSend("invalid-exchange", "invalid-routing-key", o);
    }
}
