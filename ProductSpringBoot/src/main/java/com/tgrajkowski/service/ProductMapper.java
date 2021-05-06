package com.tgrajkowski.service;

import com.tgrajkowski.model.ProductDto;
import com.tgrajkowski.model.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductEntity mapToProductEntity(ProductDto productDto) {
        return ProductEntity.builder()
                .productName(productDto.getProductName())
                .value(productDto.getValue())
                .build();
    }

    public ProductDto mapToProductDto(ProductEntity productEntity) {
        return ProductDto.builder()
                .productId(productEntity.getProductId())
                .productName(productEntity.getProductName())
                .value(productEntity.getValue())
                .build();
    }
}
