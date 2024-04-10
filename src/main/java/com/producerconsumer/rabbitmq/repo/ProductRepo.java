package com.producerconsumer.rabbitmq.repo;

import com.producerconsumer.rabbitmq.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/7/24 5:36 AM
 */
@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
}
