package com.example.statistics.rest.configuration.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Creates a {@link RestTemplate} by adding interceptor.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@Configuration
public class RestClient {

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new ClientInterceptor());
        return restTemplate;
    }
}
