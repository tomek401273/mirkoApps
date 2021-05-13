package com.tgrajkowski.service;

import com.tgrajkowski.model.ProductDto;
import com.tgrajkowski.model.ProductEntity;
import com.tgrajkowski.model.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductService productService;

    @Test
    void createProductOkTest() {
        ProductDto productDto = ProductDto.builder().productName("productName1").value(12).build();
        Integer productId =productService.createProduct(productDto);
        Optional<ProductEntity> optional= productRepository.findById(productId);
        Assert.assertNotNull(productId);
        Assert.assertTrue(optional.isPresent());
        Assert.assertEquals(productId, optional.get().getProductId());
        Assert.assertEquals(productDto.getProductName(), optional.get().getProductName());
        Assert.assertEquals(productDto.getValue(), optional.get().getValue());
    }

    @Test
    void getAllProductsOkTest() {
        ProductEntity productEntity = ProductEntity.builder().productName("productName1").value(12).build();
        ProductEntity productEntity2 = ProductEntity.builder().productName("productName12").value(13).build();
        productRepository.save(productEntity);
        productRepository.save(productEntity2);

        List<ProductDto> productDtoList= productService.getProducts();
        Assert.assertNotNull(productDtoList);
        Assert.assertEquals(2, productDtoList.size());
        Assert.assertEquals(productEntity.getProductId(), productDtoList.get(0).getProductId());
        Assert.assertEquals(productEntity.getProductName(), productDtoList.get(0).getProductName());
        Assert.assertEquals(productEntity.getValue(), productDtoList.get(0).getValue());
    }

    @Test
    void getAllProductsEmptyList() {
        List<ProductDto> productDtoList= productService.getProducts();
        Assert.assertNotNull(productDtoList);
        Assert.assertEquals(0, productDtoList.size());
    }
}
