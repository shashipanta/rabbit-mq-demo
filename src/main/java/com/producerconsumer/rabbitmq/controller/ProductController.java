package com.producerconsumer.rabbitmq.controller;

import com.producerconsumer.rabbitmq.dto.ProductDto;
import com.producerconsumer.rabbitmq.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
