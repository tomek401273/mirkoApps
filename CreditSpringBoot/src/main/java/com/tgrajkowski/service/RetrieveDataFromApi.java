package com.tgrajkowski.service;

import com.tgrajkowski.model.customer.CustomerDto;
import com.tgrajkowski.model.product.ProductDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.net.ConnectException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class RetrieveDataFromApi {
    private RestTemplate restTemplate;

    public RetrieveDataFromApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CustomerDto[] getCustomersData(URI uri) {
        try {
            return restTemplate.getForObject(uri, CustomerDto[].class);
        } catch (RestClientResponseException | ResourceAccessException | ResponseStatusException e) {
            log.warn("problem with customeService: " + e.getMessage());
        }
        return new CustomerDto[0];
    }

    public ProductDto[] getProductsData(URI uri) {
        try {
           return restTemplate.getForObject(uri, ProductDto[].class);
        } catch (RestClientResponseException | ResourceAccessException | ResponseStatusException e) {
            log.warn("problem with productService: " + e.getMessage());
        }
        return new ProductDto[0];
    }

    public Integer postCustomer(URI uri, CustomerDto customerDto) {
        try {
            return restTemplate.postForObject(uri, customerDto, Integer.class);
        } catch (RestClientResponseException | ResourceAccessException | ResponseStatusException e) {
            log.warn("problem with customerService: " + e.getMessage());
        }
        return null;
    }

    public Integer postProduct(URI uri, ProductDto customerDto) {
        try {
            return restTemplate.postForObject(uri, customerDto, Integer.class);
        } catch (RestClientResponseException | ResourceAccessException | ResponseStatusException e) {
            log.warn("problem with customerService: " + e.getMessage());
        }
        return null;
    }
}
