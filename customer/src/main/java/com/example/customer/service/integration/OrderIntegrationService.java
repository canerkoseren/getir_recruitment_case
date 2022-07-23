package com.example.customer.service.integration;

import com.example.customer.rest.configuration.RestClient;
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
