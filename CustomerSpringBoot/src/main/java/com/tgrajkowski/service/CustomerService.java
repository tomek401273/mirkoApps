package com.tgrajkowski.service;

import com.tgrajkowski.model.CustomerDto;
import com.tgrajkowski.model.CustomerEntity;
import com.tgrajkowski.model.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional
    public Integer createCustomer(CustomerDto customerDto) {
        System.out.println("createCustomer service: "+customerDto);
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findCustomerEntityByPesel(customerDto.getPesel());
        if (customerEntityOptional.isEmpty()) {
            CustomerEntity customerEntity = customerMapper.mapToCustomerEntity(customerDto);
            customerRepository.save(customerEntity);
            return customerEntity.getId();
        } else {
            return customerEntityOptional.get().getId();
        }
    }


    public CustomerDto getCustomer(Integer customerId) {
        Optional<CustomerEntity> customerOptional = customerRepository.findById(customerId);
        return customerOptional
                .map(customerMapper::maptoCustomerDto)
                .orElse(null);
    }

    public List<CustomerDto> getCustomers() {
        List<CustomerEntity> customerOptional = customerRepository.findAll();
        return customerOptional.stream()
                .map(customerMapper::maptoCustomerDto)
                .collect(Collectors.toList());
    }
}
