package com.tgrajkowski.service;

import com.tgrajkowski.model.CustomerDto;
import com.tgrajkowski.model.CustomerEntity;
import com.tgrajkowski.model.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void createCustomer() {
        CustomerDto customerDto= CustomerDto.builder()
                .firstName("FirstName")
                .pesel("pesel")
                .surname("Surname")
                .build();
        int customerId =customerService.createCustomer(customerDto);
        System.out.println(customerId);
        Optional<CustomerEntity> result = customerRepository.findById(customerId);
        System.out.println("MyData result: "+result);
    }

    @Test
    void getCustomer() {
        CustomerDto customerDto= CustomerDto.builder()
                .firstName("FirstName")
                .pesel("pesel")
                .surname("Surname")
                .build();
        int customerId =customerService.createCustomer(customerDto);
        System.out.println(customerId);
        Optional<CustomerEntity> result = customerRepository.findById(customerId);
        System.out.println("MyData2 result: "+result);

        List<CustomerEntity> all = customerRepository.findAll();
        System.out.println("size: "+all.size());
        all.forEach(System.out::println);
    }

    @Test
    void getCustomer2() {
        CustomerDto customerDto= CustomerDto.builder()
                .firstName("FirstName")
                .pesel("pesel")
                .surname("Surname")
                .build();
        int customerId =customerService.createCustomer(customerDto);
        System.out.println(customerId);
        Optional<CustomerEntity> result = customerRepository.findById(customerId);
        System.out.println("MyData2 result: "+result);

        List<CustomerEntity> all = customerRepository.findAll();
        System.out.println("size: "+all.size());
        all.forEach(System.out::println);
    }
}
