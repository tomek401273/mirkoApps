package com.tgrajkowski.service.customer;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;
import com.tgrajkowski.model.customer.CustomerDto;
import org.junit.Assert;
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
class CustomerServiceTest {
    private WireMockServer wireMockServer;
    @Autowired
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(options().port(8084));
        wireMockServer.start();
    }

    @Test
    public void getCustomersOkTest() {
        CustomerDto[] customerDtos = new CustomerDto[]{
                CustomerDto.builder().id(1).firstName("customer1").build(),
                CustomerDto.builder().id(2).firstName("customer2").build()
        };
        Gson gson = new Gson();
        String jsonContent = gson.toJson(customerDtos);

        wireMockServer.stubFor(get(urlEqualTo("/customer/"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonContent)
                )
        );
        CustomerDto[] resultCustomers = customerService.getCustomers();
        Assert.assertEquals(customerDtos.length, resultCustomers.length);
        Assert.assertEquals(customerDtos[0].getId(), resultCustomers[0].getId());
        Assert.assertEquals(customerDtos[0].getPesel(), resultCustomers[0].getPesel());
        Assert.assertEquals(customerDtos[0].getFirstName(), resultCustomers[0].getFirstName());
        Assert.assertEquals(customerDtos[0].getSurname(), resultCustomers[0].getSurname());

        Assert.assertEquals(customerDtos[1].getId(), resultCustomers[1].getId());
        Assert.assertEquals(customerDtos[1].getPesel(), resultCustomers[1].getPesel());
        Assert.assertEquals(customerDtos[1].getFirstName(), resultCustomers[1].getFirstName());
        Assert.assertEquals(customerDtos[1].getSurname(), resultCustomers[1].getSurname());
    }

    @Test
    public void getCustomersServerProblemExceptionTest() {
        CustomerDto[] customerDtos = new CustomerDto[]{};

        Gson gson = new Gson();
        String jsonContent = gson.toJson(customerDtos);
        wireMockServer.stubFor(get(urlEqualTo("/customer/"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonContent)
                )
        );
        CustomerDto[] resultCustomers = customerService.getCustomers();
        Assert.assertEquals(customerDtos.length, resultCustomers.length);
    }

    @Test
    public void getCustomersClientExceptionTest() {
        wireMockServer.stubFor(get(urlEqualTo("/customer/"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                )
        );
        CustomerDto[] resultCustomers = customerService.getCustomers();
        Assert.assertNull(resultCustomers);
    }

    @Test
    public void postCustomerOkTest() {
        Integer createdId= 1236;
        wireMockServer.stubFor(post(urlEqualTo("/customer/"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(String.valueOf(createdId))));

        CustomerDto customerDto = CustomerDto.builder()
                .firstName("FirstName")
                .pesel("71071235196")
                .surname("Surname")
                .build();
        Integer customerId = customerService.postCustomer(customerDto);
        Assert.assertEquals(createdId, customerId);
    }

    @Test
    public void postCustomerServerExceptionTest() {
        wireMockServer.stubFor(post(urlEqualTo("/customer/"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")));

        CustomerDto customerDto = CustomerDto.builder()
                .firstName("FirstName")
                .pesel("71071235196")
                .surname("Surname")
                .build();
        Integer customerId = customerService.postCustomer(customerDto);
        Assert.assertNull(customerId);
    }

    @Test
    public void createCustomerUriOkTest(){
        URI customerUri = customerService.createCustomerUri("/customer/");
        String expectedURI ="http://localhost:8084/customer/customer/";
        Assert.assertEquals(expectedURI, customerUri.toString());
    }

    @Test
    public void groupByIdOkTest(){
        CustomerDto[] customerDtos = new CustomerDto[]{
                CustomerDto.builder().id(1).firstName("customer1").build(),
                CustomerDto.builder().id(2).firstName("customer2").build()
        };
        Map<Integer, CustomerDto> idCustomerDtoMap = customerService.groupById(customerDtos);
        Assert.assertEquals(idCustomerDtoMap.size(), 2);
        Assert.assertEquals(idCustomerDtoMap.get(1), customerDtos[0]);
        Assert.assertEquals(idCustomerDtoMap.get(2), customerDtos[1]);
    }

    @Test
    public void groupByIdEmptyArrayTest(){
        CustomerDto[] customerDtos = new CustomerDto[]{};
        Map<Integer, CustomerDto> idCustomerDtoMap = customerService.groupById(customerDtos);
        Assert.assertEquals(idCustomerDtoMap.size(), 0);
    }

}
