package com.tgrajkowski.controller;

import com.google.gson.Gson;
import com.tgrajkowski.model.ProductDto;
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
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductController productController;

    private final Gson gson= new Gson();

    @Test
    void createProduct() throws Exception {
        ProductDto productDto = ProductDto.builder().productName("productName1").value(12).build();
        Mockito.when(productController.createProduct(productDto)).thenReturn(3);

        String jsonContent = gson.toJson(productDto);
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$", is(3)));
    }

    @Test
    void createProductNotValid() throws Exception {
        ProductDto productDto = ProductDto.builder().build();

        String jsonContent = gson.toJson(productDto);
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", is("Validation Problem")))
                .andExpect(jsonPath("$.detail", is("Input validation is not correct")))
                .andExpect(jsonPath("$.exceptions.productName[0].code", is("NotEmpty")))
                .andExpect(jsonPath("$.exceptions.productName[0].message", is("must not be empty")))
                .andExpect(jsonPath("$.exceptions.value[0].code", is("NotNull")))
                .andExpect(jsonPath("$.exceptions.value[0].message", is("must not be null")))
                .andExpect(jsonPath("$.developerMessage", is("org.springframework.web.bind.MethodArgumentNotValidException")));
    }

    @Test
    public void getProductListTest() throws Exception {
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(ProductDto.builder().productId(1).productName("productName1").value(12).build());
        productDtoList.add(ProductDto.builder().productId(2).productName("productName2").value(13).build());

        Mockito.when(productController.getProducts()).thenReturn(productDtoList);

        String jsonContent = gson.toJson(productDtoList);
        mockMvc.perform(get("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].productId", is(productDtoList.get(0).getProductId())))
                .andExpect(jsonPath("$[0].productName", is(productDtoList.get(0).getProductName())))
                .andExpect(jsonPath("$[0].value", is(productDtoList.get(0).getValue())))
                .andExpect(jsonPath("$[1].productId", is(productDtoList.get(1).getProductId())))
                .andExpect(jsonPath("$[1].productName", is(productDtoList.get(1).getProductName())))
                .andExpect(jsonPath("$[1].value", is(productDtoList.get(1).getValue())))
                ;
    }

    @Test
    public void getProductEmptyListTest() throws Exception {
        List<ProductDto> productDtoList = new ArrayList<>();

        Mockito.when(productController.getProducts()).thenReturn(productDtoList);

        String jsonContent = gson.toJson(productDtoList);
        mockMvc.perform(get("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$", hasSize(0)));

    }


}
