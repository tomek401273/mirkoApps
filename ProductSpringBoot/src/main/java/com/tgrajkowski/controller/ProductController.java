package com.tgrajkowski.controller;

import com.tgrajkowski.model.ProductDto;
import com.tgrajkowski.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Integer createProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @GetMapping("{productId}")
    public ProductDto getProduct(@PathVariable("productId") Integer productId) {
        return productService.getProduct(productId);
    }

    @GetMapping
    public List<ProductDto> getProduct() {
        return productService.getProducts();
    }

}
