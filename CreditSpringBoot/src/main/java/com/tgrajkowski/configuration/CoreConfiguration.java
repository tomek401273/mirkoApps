package com.tgrajkowski.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@Configuration
public class CoreConfiguration {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return builder
                .errorHandler(restTemplateResponseErrorHandler)
                .build();
    }
}
