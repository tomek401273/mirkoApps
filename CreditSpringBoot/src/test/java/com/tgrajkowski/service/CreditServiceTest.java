package com.tgrajkowski.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.tgrajkowski.configuration.ApplicationConfig;
import com.tgrajkowski.model.credit.CreditRepositoryPaging;
import com.tgrajkowski.model.customer.CustomerDto;
import com.tgrajkowski.service.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest
class CreditServiceTest {
    @Autowired
    private CreditService creditService;

    private WireMockServer wireMockServer;


    @Autowired
    private CreditMapper creditMapper;
    @Autowired
    private CreditRepositoryPaging creditRepositoryPaging;
    @Autowired
    private RetrieveDataFromApi retrieveDataFromApi;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(options().port(8084));
        wireMockServer.start();
        System.out.println("wireMockServer.port(): " + wireMockServer.port());
//        webClient = WebClient.builder().baseUrl(String.format("http://localhost:%d", wireMockServer.port())).build();
//        sessionService = new SessionService(webClient);
//        RestTemplate restTemplate=
//        retrieveDataFromGiHubApi = new RetrieveDataFromGiHubApi();
//        creditService= new CreditService(creditMapper, creditRepository, retrieveDataFromGiHubApi, applicationConfig);
    }

    @Test
    void createCredit() {
    }

    @Test
    void getCredit() {
    }

    @Test
    void getCustomer() {
    }

    @Test
    void createUriForAccountDetails() {
        wireMockServer.stubFor(post(urlEqualTo("/customer/"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("-1236")));

        CustomerDto customerDto = CustomerDto.builder()
                .firstName("FirstName")
                .pesel("71071235196")
                .surname("Surname")
                .build();
        customerService.postCustomer(customerDto);
    }
}
