package com.tgrajkowski.controller;

import com.tgrajkowski.model.CustomerDto;
import com.tgrajkowski.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Integer createCustomer(@RequestBody @Valid CustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }

    @GetMapping("{customerId}")
    public CustomerDto getCustomer(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomer(customerId);
    }

    @GetMapping
    public List<CustomerDto> getCustomer() {
        return customerService.getCustomers();
    }
}
