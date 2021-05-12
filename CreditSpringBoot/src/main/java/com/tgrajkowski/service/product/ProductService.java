package com.tgrajkowski.service.product;

import com.tgrajkowski.configuration.ApplicationConfig;
import com.tgrajkowski.model.product.ProductDto;
import com.tgrajkowski.service.RetrieveDataFromApi;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final RetrieveDataFromApi retrieveDataFromApi;
    private final ApplicationConfig applicationConfig;

    public ProductService(RetrieveDataFromApi retrieveDataFromApi, ApplicationConfig applicationConfig) {
        this.retrieveDataFromApi = retrieveDataFromApi;
        this.applicationConfig = applicationConfig;
    }

    public ProductDto[] getProducts() {
        URI uri = createProductUri("");
        ProductDto[] customerDtos = retrieveDataFromApi.getProductsData(uri);
        return customerDtos;
    }

    public Integer postProduct(ProductDto productDto) {
        URI uri = createProductUri("");
        return retrieveDataFromApi.postProduct(uri, productDto);
    }

    public URI createProductUri(String productId) {
        return UriComponentsBuilder.fromHttpUrl(applicationConfig.getProductApiLink() + "/product/" + productId)
                .build().encode().toUri();
    }

    public Map<Integer, ProductDto> groupById(ProductDto[] productDtos) {
        return Arrays.stream(productDtos).collect(Collectors.toMap(ProductDto::getProductId, productDto -> productDto));
    }
}
