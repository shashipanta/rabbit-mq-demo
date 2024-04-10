package com.producerconsumer.rabbitmq.service.impl;

import com.producerconsumer.rabbitmq.dto.ProductViewDto;
import com.producerconsumer.rabbitmq.dto.ProductViewDto;
import com.producerconsumer.rabbitmq.model.Product;
import com.producerconsumer.rabbitmq.model.ProductView;
import com.producerconsumer.rabbitmq.repo.ProductViewRepo;
import com.producerconsumer.rabbitmq.service.ProductViewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author shashi
 * @version 1.0.0
 * @since 4/7/24 7:08 AM
 */

@Service
@RequiredArgsConstructor
public class ProductViewServiceImpl implements ProductViewService {

    private final ProductViewRepo productViewRepo;
    @Override
    public Long create(ProductViewDto productViewDto) {
        ProductView productView = toEntity(productViewDto);
        return productViewRepo.save(productView).getId();
    }

    @Override
    public Long create(Product product) {
        ProductView productView = ProductView.builder()
                .id(product.getId())
                .name(product.getName())
                .manufacturerName(product.getManufacturerName())
                .price(product.getPrice())
                .build();
        return productViewRepo.save(productView).getId();
    }

    @Override
    public ProductViewDto update(ProductViewDto productViewDto, ProductView productView) {
        productViewRepo.findById(productViewDto.id())
                        .orElseThrow(() -> new EntityNotFoundException("Product View not found!"));
        prepareUpdate(productViewDto, productView);
        return toDto(productViewRepo.save(productView));
    }

    @Override
    public ProductViewDto update(Product product) {
        ProductView productView = ProductView.builder()
                .id(product.getId())
                .name(product.getName())
                .manufacturerName(product.getManufacturerName())
                .price(product.getPrice())
                .build();
        return toDto(productViewRepo.save(productView));
    }

    private ProductView toEntity(ProductViewDto productViewDto) {
        return ProductView.builder()
                .id(productViewDto.id())
                .name(productViewDto.name())
                .manufacturerName(productViewDto.manufacturerName())
                .price(productViewDto.price())
                .build();
    }

    private void prepareUpdate(ProductViewDto productViewDto, ProductView productView) {
        if (productViewDto.id() != null)
            productView.setId(productViewDto.id());
        if (productViewDto.name() != null)
            productView.setName(productViewDto.name());
        if (productViewDto.manufacturerName() != null)
            productView.setManufacturerName(productViewDto.manufacturerName());
        if (productViewDto.price() != null)
            productView.setPrice(productViewDto.price());
    }

    private ProductViewDto toDto(ProductView productView) {
        return ProductViewDto.builder()
                .id(productView.getId())
                .name(productView.getName())
                .price(productView.getPrice())
                .manufacturerName(productView.getManufacturerName())
                .build();
    }
}
