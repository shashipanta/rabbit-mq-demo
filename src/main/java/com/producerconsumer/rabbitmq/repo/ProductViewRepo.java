package com.producerconsumer.rabbitmq.repo;

import com.producerconsumer.rabbitmq.model.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/7/24 7:04 AM
 */
public interface ProductViewRepo extends JpaRepository<ProductView, Long> {
}
