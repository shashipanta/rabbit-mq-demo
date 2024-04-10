package com.producerconsumer.rabbitmq.service;

import com.producerconsumer.rabbitmq.dto.ProductDto;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/7/24 5:39 AM
 */
public interface ProductService {

    Long create(ProductDto productDto);

    ProductDto update(Long productId, ProductDto productDto);

    ProductDto view(Long productId);
}
