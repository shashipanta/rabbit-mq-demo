package com.producerconsumer.rabbitmq.listener;

import com.producerconsumer.rabbitmq.model.Product;
import com.producerconsumer.rabbitmq.service.ProductViewService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/7/24 6:51 AM
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductListener {

    private final ProductViewService productViewService;

    private static final String PRODUCT_QUEUE = "product_queue";
    private static final String DEAD_LETTER_QUEUE = "dead.letter.queue";

    @RabbitListener(queues = {PRODUCT_QUEUE})
    public void onProductCreation(Product product, Channel channel, Message message) throws IOException {
        log.info("Product registered : {}", product);
        if (product == null)
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);


        productViewService.create(product);
    }

    @RabbitListener(queues = {PRODUCT_QUEUE})
    public void onProductUpdate(Product product, Channel channel, Message message) throws IOException {
        log.info("Product updated : {}", product);
        try {
            productViewService.update(product);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

    @RabbitListener(queues = {DEAD_LETTER_QUEUE})
    public void onFailure(Object object) {
        log.info("Dead Letter Queue : {}", object);
    }
}
