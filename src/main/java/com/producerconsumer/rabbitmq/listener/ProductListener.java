package com.producerconsumer.rabbitmq.listener;

import com.producerconsumer.rabbitmq.model.Product;
import com.producerconsumer.rabbitmq.service.ProductViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

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

    @RabbitListener(queues = {PRODUCT_QUEUE})
    public void onProductCreation(Product product) {
        log.info("Product registered : {}", product);
        productViewService.create(product);
    }

    @RabbitListener(queues = {PRODUCT_QUEUE})
    public void onProductUpdate(Product product) {
        log.info("Product updated : {}", product);
        productViewService.update(product);
    }
}
