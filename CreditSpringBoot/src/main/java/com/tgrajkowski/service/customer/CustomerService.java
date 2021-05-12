package com.tgrajkowski.service.customer;

import com.tgrajkowski.configuration.ApplicationConfig;
import com.tgrajkowski.model.customer.CustomerDto;
import com.tgrajkowski.service.RetrieveDataFromApi;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final RetrieveDataFromApi retrieveDataFromApi;
    private final ApplicationConfig applicationConfig;

    public CustomerService(RetrieveDataFromApi retrieveDataFromApi, ApplicationConfig applicationConfig) {
        this.retrieveDataFromApi = retrieveDataFromApi;
        this.applicationConfig = applicationConfig;
    }

    public CustomerDto[] getCustomers() {
        URI uri = createCustomerUri("");
        CustomerDto[] customerDtos = retrieveDataFromApi.getCustomersData(uri);
        return customerDtos;
    }

    public URI createCustomerUri(String customerId) {
        return UriComponentsBuilder.fromHttpUrl(applicationConfig.getCustomerApiLink() + "/customer/" + customerId)
                .build().encode().toUri();
    }

    public Integer postCustomer(CustomerDto customerDto) {
        URI uri = createCustomerUri("");
        return retrieveDataFromApi.postCustomer(uri, customerDto);
    }

    public Map<Integer, CustomerDto> groupById(CustomerDto[] customers) {
        return Arrays.stream(customers)
                .collect(Collectors.toMap(CustomerDto::getId, customerDto -> customerDto));
    }

}
