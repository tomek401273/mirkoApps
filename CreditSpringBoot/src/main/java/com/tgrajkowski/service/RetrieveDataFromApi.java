package com.tgrajkowski.service;

import com.tgrajkowski.model.customer.CustomerDto;
import com.tgrajkowski.model.product.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class RetrieveDataFromApi {
    private RestTemplate restTemplate;

    public RetrieveDataFromApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CustomerDto[] getCustomersData(URI uri) {
        return restTemplate.getForObject(uri, CustomerDto[].class);
    }

    public ProductDto[] getProductsData(URI uri) {
        return restTemplate.getForObject(uri, ProductDto[].class);
    }

    public Integer postCustomer(URI uri, CustomerDto customerDto){
        return restTemplate.postForObject(uri,customerDto, Integer.class);
    }

    public Integer postProduct(URI uri, ProductDto customerDto){
        return restTemplate.postForObject(uri, customerDto, Integer.class);
    }
}
