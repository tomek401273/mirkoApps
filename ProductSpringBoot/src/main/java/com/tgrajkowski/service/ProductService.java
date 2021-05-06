package com.tgrajkowski.service;

import com.tgrajkowski.model.ProductDto;
import com.tgrajkowski.model.ProductEntity;
import com.tgrajkowski.model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public ProductDto getProduct(Integer productId) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        return productEntityOptional.map(productMapper::mapToProductDto)
                .orElse(null);
    }

    @Transactional
    public Integer createProduct(ProductDto productDto) {
        Optional<ProductEntity> productEntityOptional = productRepository
                .findProductEntitiesByProductNameAndValue(productDto.getProductName(), productDto.getValue());
        if (productEntityOptional.isEmpty()){
            ProductEntity productEntity = productMapper.mapToProductEntity(productDto);
            productRepository.save(productEntity);
            return productEntity.getProductId();
        }
        else {
            return productEntityOptional.get().getProductId();
        }
    }
}
