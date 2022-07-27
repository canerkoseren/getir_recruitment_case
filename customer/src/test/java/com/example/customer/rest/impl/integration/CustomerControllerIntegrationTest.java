package com.example.customer.rest.impl.integration;

import com.example.customer.CustomerApplication;
import com.example.customer.rest.CustomerController;
import com.example.customer.rest.configuration.constant.Headers;
import com.example.customer.service.model.CustomerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

/**Integration test for {@link CustomerController}.
 *
* @author Caner Köseren
* @version 0.0.1
* @created 27.7.2022
*/
@SpringBootTest(classes = CustomerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerIntegrationTest {

    private static HttpHeaders httpHeaders;
    private static CustomerDto customer;
    private static String urlPrefix;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    static void setUp() {

        urlPrefix = "http://localhost:8091/customer/v1";

        httpHeaders = new HttpHeaders();
        httpHeaders.add(Headers.AUTHORIZATION, "token123");

        customer = new CustomerDto();
        customer.setName("cust-int-name");
        customer.setLastName("cust-int-last-name");
        customer.setEmail("cust-int-email" + LocalDateTime.now());
    }

    @Test
    @Order(1)
    void testSave() {

        String url = urlPrefix + "/save";
        HttpEntity httpEntity = new HttpEntity(customer, httpHeaders);
        ResponseEntity<CustomerDto> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, CustomerDto.class);
        customer = response.getBody();
        Assertions.assertNotNull(customer);
    }

    @Test
    @Order(2)
    void testFindById() {

        String url = urlPrefix + "/findById?customerId=" + customer.getId();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<CustomerDto> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, CustomerDto.class);
        customer = response.getBody();
        Assertions.assertNotNull(customer);
    }

    @Test
    @Order(3)
    void testFindByEmail() {

        String url = urlPrefix + "/findByEmail?email=" + customer.getEmail();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<CustomerDto> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, CustomerDto.class);
        customer = response.getBody();
        Assertions.assertNotNull(customer);
    }
}
