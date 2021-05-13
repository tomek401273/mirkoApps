package com.tgrajkowski.service;

import com.tgrajkowski.model.CustomerDto;
import com.tgrajkowski.model.CustomerEntity;
import com.tgrajkowski.model.CustomerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void beforeEach(){
        customerRepository.deleteAll();
    }

    @Test
    void createCustomerOkTest() {
        CustomerDto customerDto= CustomerDto.builder()
                .firstName("FirstName")
                .pesel("pesel")
                .surname("Surname")
                .build();
        Integer customerId =customerService.createCustomer(customerDto);
        Optional<CustomerEntity> result = customerRepository.findById(customerId);

        Assert.assertNotNull(customerId);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(customerId, result.get().getId());
        Assert.assertEquals(customerDto.getFirstName(), result.get().getFirstName());
        Assert.assertEquals(customerDto.getPesel(), result.get().getPesel());
        Assert.assertEquals(customerDto.getSurname(), result.get().getSurname());
    }

    @Test
    void getCustomerOKTest() {
        CustomerDto customerDto= CustomerDto.builder()
                .firstName("FirstName")
                .pesel("pesel")
                .surname("Surname")
                .build();
        Integer customerId =customerService.createCustomer(customerDto);
        CustomerDto result = customerService.getCustomer(customerId);

        Assert.assertNotNull(result);
        Assert.assertEquals(customerId, result.getId());
        Assert.assertEquals(customerDto.getFirstName(), result.getFirstName());
        Assert.assertEquals(customerDto.getPesel(), result.getPesel());
        Assert.assertEquals(customerDto.getSurname(), result.getSurname());
    }

    @Test
    void getCustomerNotFoundTest() {
        CustomerDto result = customerService.getCustomer(-1);
        Assert.assertNull(result);
    }

    @Test
    void getCustomerListTwoElements() {
        List<CustomerEntity> customerDtoList = new ArrayList<>();
        customerDtoList.add(CustomerEntity.builder()
                .firstName("FirstName")
                .pesel("pesel")
                .surname("Surname")
                .build());

        customerDtoList.add(CustomerEntity.builder()
                .firstName("FirstName2")
                .pesel("pesel2")
                .surname("Surname2")
                .build());
        customerRepository.saveAll(customerDtoList);

        List<CustomerEntity> all = customerRepository.findAll();

        Assert.assertEquals(2, all.size());
        Assert.assertNotNull(all.get(0).getId());
        Assert.assertEquals(customerDtoList.get(0).getFirstName(), all.get(0).getFirstName());
        Assert.assertEquals(customerDtoList.get(0).getSurname(), all.get(0).getSurname());
        Assert.assertEquals(customerDtoList.get(0).getPesel(), all.get(0).getPesel());
        Assert.assertNotNull(all.get(1).getId());
        Assert.assertEquals(customerDtoList.get(1).getFirstName(), all.get(1).getFirstName());
        Assert.assertEquals(customerDtoList.get(1).getSurname(), all.get(1).getSurname());
        Assert.assertEquals(customerDtoList.get(1).getPesel(), all.get(1).getPesel());
    }

    @Test
    void getCustomerEmptyList() {
        List<CustomerEntity> all = customerRepository.findAll();
        Assert.assertEquals(0, all.size());
    }
}
