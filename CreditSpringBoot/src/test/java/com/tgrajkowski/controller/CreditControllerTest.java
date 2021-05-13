package com.tgrajkowski.controller;

import com.google.gson.Gson;
import com.tgrajkowski.model.CreditCustomerProductDto;
import com.tgrajkowski.model.credit.CreditDto;
import com.tgrajkowski.model.customer.CustomerDto;
import com.tgrajkowski.model.product.ProductDto;
import org.junit.Assert;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CreditController.class)
class CreditControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CreditController creditController;
    private final Gson gson = new Gson();

    @Test
    void createCredit() throws Exception {
        CreditCustomerProductDto creditCustomerProductDto = CreditCustomerProductDto.builder()
                .creditDto(CreditDto.builder().creditName("creditName1").build())
                .productDto(ProductDto.builder().productName("productName1").value(12).build())
                .customerDto(CustomerDto.builder()
                        .firstName("FirstName")
                        .pesel("71071235196")
                        .surname("Surname")
                        .build())
                .build();

        Integer creditId = 1;
        Mockito.when(creditController.createCredit(creditCustomerProductDto)).thenReturn(creditId);

        String jsonContent = gson.toJson(creditCustomerProductDto);
        mockMvc.perform(post("/credit")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(1)));
    }


    @Test
    void createCreditProductDtoNotValid() throws Exception {
        CreditCustomerProductDto creditCustomerProductDto = CreditCustomerProductDto.builder()
                .creditDto(CreditDto.builder().creditName("creditName1").build())
                .productDto(ProductDto.builder().build())
                .customerDto(CustomerDto.builder().id(1).firstName("customer1").build())
                .build();

        Integer creditId = 1;
        Mockito.when(creditController.createCredit(creditCustomerProductDto)).thenReturn(creditId);

        String jsonContent = gson.toJson(creditCustomerProductDto);
        mockMvc.perform(post("/credit")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", is("Validation Problem")))
                .andExpect(jsonPath("$.detail", is("Input validation is not correct")))
                .andExpect(jsonPath("$.exceptions.['productDto.productName'][0].code", is("NotEmpty")))
                .andExpect(jsonPath("$.exceptions.['productDto.productName'][0].message", is("must not be empty")))
                .andExpect(jsonPath("$.exceptions.['productDto.value'][0].code", is("NotNull")))
                .andExpect(jsonPath("$.exceptions.['productDto.value'][0].message", is("must not be null")))
                .andExpect(jsonPath("$.developerMessage", is("org.springframework.web.bind.MethodArgumentNotValidException")));
    }


    @Test
    void createCreditCustomerDtoNotValid() throws Exception {
        CreditCustomerProductDto creditCustomerProductDto = CreditCustomerProductDto.builder()
                .creditDto(CreditDto.builder().creditName("creditName1").build())
                .productDto(ProductDto.builder().productName("productName1").value(12).build())
                .customerDto(CustomerDto.builder()
                        .pesel("1234")
                        .build())
                .build();

        Integer creditId = 1;
        Mockito.when(creditController.createCredit(creditCustomerProductDto)).thenReturn(creditId);

        String jsonContent = gson.toJson(creditCustomerProductDto);
        mockMvc.perform(post("/credit")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", is("Validation Problem")))
                .andExpect(jsonPath("$.detail", is("Input validation is not correct")))
                .andExpect(jsonPath("$.exceptions.['customerDto.firstName'][0].code", is("NotEmpty")))
                .andExpect(jsonPath("$.exceptions.['customerDto.firstName'][0].message", is("must not be empty")))
                .andExpect(jsonPath("$.exceptions.['customerDto.surname'][0].code", is("NotEmpty")))
                .andExpect(jsonPath("$.exceptions.['customerDto.surname'][0].message", is("must not be empty")))
                .andExpect(jsonPath("$.exceptions.['customerDto.pesel'][0].code", is("PESEL")))
                .andExpect(jsonPath("$.exceptions.['customerDto.pesel'][0].message", is("invalid Polish National Identification Number (PESEL)")))
                .andExpect(jsonPath("$.developerMessage", is("org.springframework.web.bind.MethodArgumentNotValidException")));
    }

    @Test
    void createCreditCustomerObjectNotValidFirstnameAndSurnameTooLongPeselNullTest() throws Exception {
        CreditCustomerProductDto creditCustomerProductDto = CreditCustomerProductDto.builder()
                .creditDto(CreditDto.builder().creditName("creditName1").build())
                .productDto(ProductDto.builder().productName("productName1").value(12).build())
                .customerDto(CustomerDto.builder()
                        .firstName("firstNamefirstName12345")
                        .surname("Surname")
                        .build())
                .build();

        Integer creditId = 1;
        Mockito.when(creditController.createCredit(creditCustomerProductDto)).thenReturn(creditId);

        String jsonContent = gson.toJson(creditCustomerProductDto);
        mockMvc.perform(post("/credit")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", is("Validation Problem")))
                .andExpect(jsonPath("$.detail", is("Input validation is not correct")))
                .andExpect(jsonPath("$.exceptions.['customerDto.firstName'][0].code", is("Size")))
                .andExpect(jsonPath("$.exceptions.['customerDto.firstName'][0].message", is("size must be between 0 and 20")))
                .andExpect(jsonPath("$.exceptions.['customerDto.pesel'][0].code", is("NotEmpty")))
                .andExpect(jsonPath("$.exceptions.['customerDto.pesel'][0].message", is("must not be empty")))
                .andExpect(jsonPath("$.developerMessage", is("org.springframework.web.bind.MethodArgumentNotValidException")));
    }

    @Test
    void createCreditDtoNotValidTest() throws Exception {
        CreditCustomerProductDto creditCustomerProductDto = CreditCustomerProductDto.builder()
                .creditDto(CreditDto.builder().build())
                .productDto(ProductDto.builder().productName("productName1").value(12).build())
                .customerDto(CustomerDto.builder().id(1).firstName("customer1").build())
                .build();

        Integer creditId = 1;
        Mockito.when(creditController.createCredit(creditCustomerProductDto)).thenReturn(creditId);

        String jsonContent = gson.toJson(creditCustomerProductDto);
        mockMvc.perform(post("/credit")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", is("Validation Problem")))
                .andExpect(jsonPath("$.detail", is("Input validation is not correct")))
                .andExpect(jsonPath("$.exceptions.['creditDto.creditName'][0].code", is("NotEmpty")))
                .andExpect(jsonPath("$.exceptions.['creditDto.creditName'][0].message", is("must not be empty")))
                .andExpect(jsonPath("$.developerMessage", is("org.springframework.web.bind.MethodArgumentNotValidException")));
    }

    @Test
    void getCredit() {
    }

    @Test
    void getAllCreditsTest() throws Exception {
        List<CreditCustomerProductDto> creditCustomerProductDtoList = new ArrayList<>();
        creditCustomerProductDtoList.add(CreditCustomerProductDto.builder()
                .creditDto(CreditDto.builder().creditId(12).creditName("creditName1").build())
                .productDto(ProductDto.builder().productId(123).productName("productName1").value(12).build())
                .customerDto(CustomerDto.builder()
                        .id(112)
                        .firstName("FirstName")
                        .pesel("71071235196")
                        .surname("Surname")
                        .build())
                .build());
        creditCustomerProductDtoList.add(CreditCustomerProductDto.builder()
                .creditDto(CreditDto.builder().creditName("creditName2").build())
                .productDto(ProductDto.builder().productId(124).productName("productName2").value(13).build())
                .customerDto(CustomerDto.builder()
                        .firstName("FirstName2")
                        .pesel("71071235198")
                        .surname("Surname1")
                        .build())
                .build());

        Mockito.when(creditController.getAllCredit()).thenReturn(creditCustomerProductDtoList);
        mockMvc.perform(get("/credit")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].creditDto.creditId", is(12)))
                .andExpect(jsonPath("$[0].creditDto.creditName", is("creditName1")))
                .andExpect(jsonPath("$[0].customerDto.id", is(112)))
                .andExpect(jsonPath("$[0].customerDto.firstName", is("FirstName")))
                .andExpect(jsonPath("$[0].customerDto.pesel", is("71071235196")))
                .andExpect(jsonPath("$[0].productDto.productId", is(123)))
                .andExpect(jsonPath("$[0].productDto.productName", is("productName1")))
                .andExpect(jsonPath("$[0].productDto.value", is(12)))

        ;
    }

    @Test
    void getAllCreditsEmptyListTest() throws Exception {
        List<CreditCustomerProductDto> creditCustomerProductDtoList = new ArrayList<>();

        Mockito.when(creditController.getAllCredit()).thenReturn(creditCustomerProductDtoList);
        mockMvc.perform(get("/credit")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
        ;
    }
}
