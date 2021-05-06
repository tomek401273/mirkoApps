package com.tgrajkowski.service;

import com.tgrajkowski.model.CustomerDto;
import com.tgrajkowski.model.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerEntity mapToCustomerEntity(CustomerDto customerDto) {
        return CustomerEntity.builder()
                .firstName(customerDto.getFirstName())
                .pesel(customerDto.getPesel())
                .surname(customerDto.getSurname())
                .build();
    }

    public CustomerDto maptoCustomerDto(CustomerEntity customerEntity) {
        return CustomerDto.builder()
                .id(customerEntity.getId())
                .firstName(customerEntity.getFirstName())
                .pesel(customerEntity.getPesel())
                .surname(customerEntity.getSurname())
                .build();
    }
}
