package com.producerconsumer.rabbitmq.service.impl;

import com.producerconsumer.rabbitmq.dto.ProductDto;
import com.producerconsumer.rabbitmq.model.Product;
import com.producerconsumer.rabbitmq.publisher.ProductPublisher;
import com.producerconsumer.rabbitmq.repo.ProductRepo;
import com.producerconsumer.rabbitmq.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/7/24 5:41 AM
 */

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductPublisher productPublisher;

    @Override
    public Long create(ProductDto productDto) {
        Product product = toEntity(productDto);
        product = productRepo.save(product);
        // todo: make this async
        productPublisher.createProduct(product);
        return product.getId();
    }

    @Override
    public ProductDto update(Long productId, ProductDto productDto) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        prepareUpdate(productDto, product);
        product = productRepo.save(product);
        // todo: make this async
        productPublisher.updateProduct(product);
        return toDto(productRepo.save(product));
    }

    @Override
    public ProductDto view(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return toDto(product);
    }

    @Override
    public void invalidExchange() {
        productPublisher.invalidExchange(new Product());
    }

    private Product toEntity(ProductDto productDto) {
        return Product.builder()
                .id(productDto.id())
                .name(productDto.name())
                .nameNp(productDto.nameNp())
                .manufacturerName(productDto.manufacturerName())
                .productSerialNo(productDto.productSerialNo())
                .price(productDto.price())
                .build();
    }

    private void prepareUpdate(ProductDto productDto, Product product) {
        if (productDto.id() != null)
            product.setId(productDto.id());
        if (productDto.name() != null)
            product.setName(productDto.name());
        if (productDto.nameNp() != null)
            product.setNameNp(productDto.nameNp());
        if (productDto.productSerialNo() != null)
            product.setProductSerialNo(productDto.productSerialNo());
        if (productDto.manufacturerName() != null)
            product.setManufacturerName(productDto.manufacturerName());
        if (productDto.price() != null)
            product.setPrice(productDto.price());
    }

    private ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .nameNp(product.getNameNp())
                .price(product.getPrice())
                .manufacturerName(product.getManufacturerName())
                .productSerialNo(product.getProductSerialNo())
                .build();
    }
}
