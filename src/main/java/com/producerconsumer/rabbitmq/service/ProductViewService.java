package com.producerconsumer.rabbitmq.service;

import com.producerconsumer.rabbitmq.dto.ProductViewDto;
import com.producerconsumer.rabbitmq.model.Product;
import com.producerconsumer.rabbitmq.model.ProductView;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/7/24 7:05 AM
 */
public interface ProductViewService {

    Long create(ProductViewDto productViewDto);

    Long create(Product product);

    ProductViewDto update(ProductViewDto productViewDto, ProductView productView);

    ProductViewDto update(Product product);
}
