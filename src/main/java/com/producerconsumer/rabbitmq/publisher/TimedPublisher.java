package com.producerconsumer.rabbitmq.publisher;

import com.producerconsumer.rabbitmq.config.RabbitMqConstants;
import com.producerconsumer.rabbitmq.model.Product;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/10/24 7:23 AM
 */

@Component
@RequiredArgsConstructor
public class TimedPublisher {

    private final RabbitTemplate rabbitTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(TimedPublisher.class);

    // Scheduled task to send an object every 5 seconds
//    @Scheduled(fixedDelay = 5000)
//    public void sender() {
//        Product product = new Product();
//        LOGGER.info("Sending example object at : {}" , LocalDateTime.now());
//        rabbitTemplate
//                .convertAndSend(RabbitMqConstants.PRODUCT_EXCHANGE, RabbitMqConstants.PRODUCT_ROUTING_KEY, product);
//    }

    public void periodicSender() {
        Product product = new Product();
        LOGGER.info("Sending example object at : {}" , LocalDateTime.now());
        for(int i=0; i<5; i++) {
            rabbitTemplate
                    .convertAndSend(RabbitMqConstants.PRODUCT_EXCHANGE, RabbitMqConstants.PRODUCT_ROUTING_KEY, product);
        }

    }

    @RabbitListener(queues = RabbitMqConstants.DEAD_LETTER_QUEUE)
    public void handleMessage(ExampleObject exampleObject) {
        LOGGER.info("Received incoming object at " + exampleObject.getDate());
    }

    @Getter
    public static class ExampleObject {
        LocalDateTime date;
        ExampleObject() {
            date = LocalDateTime.now();
        }
    }
}