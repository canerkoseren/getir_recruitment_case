package com.example.statistics.rest.impl.integration;

import com.example.statistics.StatisticsApplication;
import com.example.statistics.rest.StatisticsController;
import com.example.statistics.rest.configuration.constant.Headers;
import com.example.statistics.service.model.BookDto;
import com.example.statistics.service.model.CustomerDto;
import com.example.statistics.service.model.OrderDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Integration test for {@link StatisticsController}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 28.7.2022
 */
@SpringBootTest(classes = StatisticsApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StatisticsControllerIntegrationTest {

    private HttpHeaders httpHeaders;
    private CustomerDto customer;
    private BookDto book;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeEach
    void setUp() {

        httpHeaders = new HttpHeaders();
        httpHeaders.add(Headers.AUTHORIZATION, "token123");
    }

    @Test
    void getMonthlyReport() {

        createCustomer();
        createBook();
        createOrder();

        String url = "http://localhost:8083/statistics/v1/monthlyCustomerOrders?customerId=" + customer.getId();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        Object response = restTemplate.exchange(url,HttpMethod.GET, httpEntity, Object.class);
        Assertions.assertNotNull(response);
    }

    private void createCustomer() {

        customer = new CustomerDto();
        customer.setName("name");
        customer.setLastName("last-name");
        customer.setEmail("email" + LocalDateTime.now());

        String url = "http://localhost:8091/customer/v1/save";
        HttpEntity httpEntity = new HttpEntity(customer, httpHeaders);
        ResponseEntity<CustomerDto> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, CustomerDto.class);
        customer = response.getBody();
        Assertions.assertNotNull(customer);
    }

    private void createBook() {

        book = new BookDto();
        book.setDescription("test-description");
        book.setTitle("test-title-" + LocalDateTime.now());
        book.setKind("test-kind");
        book.setStock(150l);
        book.setWriter("test-writer");

        String url = "http://localhost:8080/book/v1/save";
        HttpEntity httpEntity = new HttpEntity(book, httpHeaders);
        ResponseEntity<BookDto> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, BookDto.class);
        book = response.getBody();
        Assertions.assertNotNull(book);
    }

    private void createOrder() {

        OrderDto order = new OrderDto();
        order.setCustomerId(customer.getId());
        order.setBookIdList(Arrays.asList(book.getId()));
        order.setAmount(152D);
        order.setProcessDate(LocalDate.now());
        order.setStatus("OK");

        String url = "http://localhost:8092/order/v1/save";
        HttpEntity httpEntity = new HttpEntity(order, httpHeaders);
        ResponseEntity<OrderDto> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, OrderDto.class);
        order = response.getBody();
        Assertions.assertNotNull(order);
    }
}
