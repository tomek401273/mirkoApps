package com.tgrajkowski.controller;

import com.google.gson.Gson;
import com.tgrajkowski.model.CustomerDto;
import com.tgrajkowski.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;

    @Test
    void createCustomer() throws Exception {
        CustomerDto customerDto = CustomerDto.builder()
                .firstName("FirstName")
                .pesel("71071235196")
                .surname("Surname")
                .build();
        Mockito.when(customerService.createCustomer(customerDto)).thenReturn(4);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(customerDto);
        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$", is(4)));
    }

    @Test
    void createCustomerObjectNotValid() throws Exception {
        CustomerDto customerDto = CustomerDto.builder()
                .pesel("1234")
                .build();
        Mockito.when(customerService.createCustomer(customerDto)).thenReturn(4);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(customerDto);
        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.title", is("Validation Problem")))
                .andExpect(jsonPath("$.detail", is("Input validation is not correct")))
                .andExpect(jsonPath("$.exceptions.firstName[0].code", is("NotEmpty")))
                .andExpect(jsonPath("$.exceptions.firstName[0].message", is("must not be empty")))
                .andExpect(jsonPath("$.exceptions.surname[0].code", is("NotEmpty")))
                .andExpect(jsonPath("$.exceptions.surname[0].message", is("must not be empty")))
                .andExpect(jsonPath("$.exceptions.pesel[0].code", is("PESEL")))
                .andExpect(jsonPath("$.exceptions.pesel[0].message", is("invalid Polish National Identification Number (PESEL)")))
                .andExpect(jsonPath("$.developerMessage", is("org.springframework.web.bind.MethodArgumentNotValidException")))
        ;
    }

    @Test
    void createCustomerObjectNotValidFirstnameAndSurnameTooLongPeselNullTest() throws Exception {
        CustomerDto customerDto = CustomerDto.builder()
                .firstName("firstNamefirstName12345")
                .surname("Surname")
                .build();
        Mockito.when(customerService.createCustomer(customerDto)).thenReturn(4);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(customerDto);
        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", is("Validation Problem")))
                .andExpect(jsonPath("$.detail", is("Input validation is not correct")))
                .andExpect(jsonPath("$.exceptions.firstName[0].code", is("Length")))
                .andExpect(jsonPath("$.exceptions.firstName[0].message", is("length must be between 0 and 20")))
                .andExpect(jsonPath("$.exceptions.pesel[0].code", is("NotEmpty")))
                .andExpect(jsonPath("$.exceptions.pesel[0].message", is("must not be empty")))
                .andExpect(jsonPath("$.developerMessage", is("org.springframework.web.bind.MethodArgumentNotValidException")))
        ;
    }

    @Test
    void getCustomer() throws Exception {
        CustomerDto customerDto = CustomerDto.builder()
                .id(1)
                .firstName("FirstName")
                .pesel("pesel")
                .surname("Surname")
                .build();
        int id = 1;
        Mockito.when(customerService.getCustomer(id)).thenReturn(customerDto);

        mockMvc.perform(get("/customer/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("FirstName")))
                .andExpect(jsonPath("$.surname", is("Surname")))
                .andExpect(jsonPath("$.id", is(id)))
        ;
    }

    @Test
    void getCustomers() throws Exception {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        customerDtoList.add(CustomerDto.builder()
                .id(1)
                .firstName("FirstName")
                .pesel("pesel")
                .surname("Surname")
                .build());

        customerDtoList.add(CustomerDto.builder()
                .id(2)
                .firstName("FirstName2")
                .pesel("pesel2")
                .surname("Surname2")
                .build());


        Mockito.when(customerService.getCustomers()).thenReturn(customerDtoList);

        mockMvc.perform(get("/customer/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(customerDtoList.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].surname", is(customerDtoList.get(0).getSurname())))
                .andExpect(jsonPath("$[0].id", is(customerDtoList.get(0).getId())))
                .andExpect(jsonPath("$[0].pesel", is(customerDtoList.get(0).getPesel())))

                .andExpect(jsonPath("$[1].firstName", is(customerDtoList.get(1).getFirstName())))
                .andExpect(jsonPath("$[1].surname", is(customerDtoList.get(1).getSurname())))
                .andExpect(jsonPath("$[1].id", is(customerDtoList.get(1).getId())))
                .andExpect(jsonPath("$[1].pesel", is(customerDtoList.get(1).getPesel())))
        ;
    }

    @Test
    void getCustomersEmptyList() throws Exception {
        List<CustomerDto> customerDtoList = new ArrayList<>();

        Mockito.when(customerService.getCustomers()).thenReturn(customerDtoList);

        mockMvc.perform(get("/customer/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
        ;
    }
}
