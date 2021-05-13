package com.tgrajkowski.service.product;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;
import com.tgrajkowski.model.product.ProductDto;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTest {
    private static WireMockServer wireMockServer;

    @Autowired
    private ProductService productService;

    @BeforeAll
    public static void setup() {
        wireMockServer = new WireMockServer(options().port(8085));
        wireMockServer.start();
    }

    @Test
    public void getProductsOkTest() {
        ProductDto[] productDtos = new ProductDto[]{
                ProductDto.builder().productId(1).productName("productName1").value(12).build(),
                ProductDto.builder().productId(2).productName("productName2").value(13).build()
        };

        Gson gson = new Gson();
        String jsonContent = gson.toJson(productDtos);

        wireMockServer.stubFor(get(urlEqualTo("/product/"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonContent)
                )
        );
        ProductDto[] productDtosResult = productService.getProducts();
        Assert.assertEquals(productDtos.length, productDtosResult.length);
        Assert.assertEquals(productDtos[0].getProductId(), productDtosResult[0].getProductId());
        Assert.assertEquals(productDtos[0].getProductName(), productDtosResult[0].getProductName());
        Assert.assertEquals(productDtos[0].getValue(), productDtosResult[0].getValue());

        Assert.assertEquals(productDtos[1].getProductId(), productDtosResult[1].getProductId());
        Assert.assertEquals(productDtos[1].getProductName(), productDtosResult[1].getProductName());
        Assert.assertEquals(productDtos[1].getValue(), productDtosResult[1].getValue());
    }

    @Test
    public void getProductsServerProblemExceptionTest() {
        ProductDto[] productDtos = new ProductDto[]{};

        Gson gson = new Gson();
        String jsonContent = gson.toJson(productDtos);
        wireMockServer.stubFor(get(urlEqualTo("/product/"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonContent)
                )
        );
        ProductDto[] resultProducts = productService.getProducts();
        Assert.assertEquals(productDtos.length, resultProducts.length);
    }

    @Test
    public void getProductClientExceptionTest() {
        wireMockServer.stubFor(get(urlEqualTo("/product/"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                )
        );
        ProductDto[] resultProductArray = productService.getProducts();
        Assert.assertNull(resultProductArray);
    }

    @Test
    public void postProductOkTest() {
        Integer createdId = 1233;
        wireMockServer.stubFor(post(urlEqualTo("/product/"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(String.valueOf(createdId))));

        ProductDto productDto = ProductDto.builder()
                .productId(1)
                .productName("productName1")
                .value(12)
                .build();
        Integer productId = productService.postProduct(productDto);
        Assert.assertEquals(createdId, productId);
    }

    @Test
    public void postProductServerExceptionTest() {
        wireMockServer.stubFor(post(urlEqualTo("/product/"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")));

        ProductDto productDto = ProductDto.builder()
                .productId(1)
                .productName("productName1")
                .value(12)
                .build();
        Integer productId = productService.postProduct(productDto);
        Assert.assertNull(productId);
    }

    @Test
    public void createProductUriOkTest() {
        URI productUri = productService.createProductUri("/product/");
        String expectedURI = "http://localhost:8085/product/product/";
        Assert.assertEquals(expectedURI, productUri.toString());
    }

    @Test
    public void groupByIdOkTest() {
        ProductDto[] productDtos = new ProductDto[]{
                ProductDto.builder().productId(1).productName("productName1").value(12).build(),
                ProductDto.builder().productId(2).productName("productName2").value(13).build()
        };
        Map<Integer, ProductDto> idProductDtoMap = productService.groupById(productDtos);
        Assert.assertEquals(idProductDtoMap.size(), 2);
        Assert.assertEquals(idProductDtoMap.get(1), productDtos[0]);
        Assert.assertEquals(idProductDtoMap.get(2), productDtos[1]);
    }

    @Test
    public void groupByIdEmptyArrayTest() {
        ProductDto[] productDtos = new ProductDto[]{};
        Map<Integer, ProductDto> idProductDtoMap = productService.groupById(productDtos);
        Assert.assertEquals(idProductDtoMap.size(), 0);
    }
}
