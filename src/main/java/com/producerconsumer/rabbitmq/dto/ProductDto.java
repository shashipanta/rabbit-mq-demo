package com.producerconsumer.rabbitmq.dto;

import lombok.Builder;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/7/24 5:39 AM
 */

@Builder
public record ProductDto(
        Long id,
        String name,
        String nameNp,
        String manufacturerName,
        String productSerialNo,
        Long price) {
}
