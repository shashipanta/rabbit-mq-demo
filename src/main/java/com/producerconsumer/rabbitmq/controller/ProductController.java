package com.producerconsumer.rabbitmq.controller;

import com.producerconsumer.rabbitmq.config.RabbitMqConstants;
import com.producerconsumer.rabbitmq.dto.ProductDto;
import com.producerconsumer.rabbitmq.model.Product;
import com.producerconsumer.rabbitmq.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/7/24 6:01 AM
 */

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseEntity<Object> registerProduct(@RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.create(productDto), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductDto productDto, @PathVariable("productId") Long productId) {
        return new ResponseEntity<>(productService.update(productId, productDto), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> getProduct(@PathVariable("productId") Long productId) {
        return new ResponseEntity<>(productService.view(productId), HttpStatus.OK);
    }


    // add message to dead letter queue : jpa exception
    @GetMapping("/invalid")
    public ResponseEntity<Object> invalidExchangeName() {
        periodicSender();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public void periodicSender() {
        Product product = new Product();
        LOGGER.info("Sending example object at : {}" , LocalDateTime.now());
        for(int i=0; i<3; i++) {
            rabbitTemplate
                    .convertAndSend(RabbitMqConstants.PRODUCT_EXCHANGE, RabbitMqConstants.PRODUCT_ROUTING_KEY, product);
        }

    }

}
