package com.tgrajkowski.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {
    @Value("${customer.spring.boot.link}")
    private String customerApiLink;

    @Value("${product.spring.boot.link}")
    private String productApiLink;

    public String getProductApiLink() {
        return productApiLink;
    }


    public String getCustomerApiLink() {
        return customerApiLink;
    }
}
