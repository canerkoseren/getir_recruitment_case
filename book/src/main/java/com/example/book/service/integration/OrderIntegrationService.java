package com.example.book.service.integration;

import com.example.book.rest.configuration.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderIntegrationService {

    private RestClient restClient;

    @Autowired
    public OrderIntegrationService(RestClient restClient) {
        this.restClient = restClient;
    }
}
