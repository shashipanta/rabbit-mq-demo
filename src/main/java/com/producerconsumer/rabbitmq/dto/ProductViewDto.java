package com.producerconsumer.rabbitmq.dto;

import lombok.Builder;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/7/24 7:07 AM
 */

@Builder
public record ProductViewDto(
        Long id,
        String name,
        String manufacturerName,
        Long price
) {

}
