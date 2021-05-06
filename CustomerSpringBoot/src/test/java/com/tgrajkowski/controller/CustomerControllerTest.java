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
        CustomerDto customerDto= CustomerDto.builder()
                .firstName("FirstName")
                .pesel("71071235196")
                .surname("Surname")
                .build();
        Mockito.when(customerService.createCustomer(customerDto)).thenReturn(4);

        Gson gson = new Gson();
        String jsonContent= gson.toJson(customerDto);
        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        .content(jsonContent))
                .andExpect(jsonPath("$", is(4)));
    }

    @Test
    void getCustomer() throws Exception {
        CustomerDto customerDto= CustomerDto.builder()
                .id(1)
                .firstName("FirstName")
                .pesel("pesel")
                .surname("Surname")
                .build();
        int id = 1;
        Mockito.when(customerService.getCustomer(id)).thenReturn(customerDto);

        mockMvc.perform(get("/customer/"+id)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("FirstName")))
                .andExpect(jsonPath("$.surname", is("Surname")))
                .andExpect(jsonPath("$.id", is(id)))
        ;
    }
}
