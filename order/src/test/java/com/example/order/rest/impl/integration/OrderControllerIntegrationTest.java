package com.example.order.rest.impl.integration;

import com.example.order.OrderApplication;
import com.example.order.rest.OrderController;
import com.example.order.rest.configuration.constant.Headers;
import com.example.order.service.model.BookDto;
import com.example.order.service.model.CustomerDto;
import com.example.order.service.model.OrderDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
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
import java.util.Collections;

/**
 * Integration test for {@link OrderController}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 27.7.2022
 */
@SpringBootTest(classes = OrderApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerIntegrationTest {

    private static HttpHeaders httpHeaders;
    private static CustomerDto customer;
    private static BookDto book;
    private static OrderDto order;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {

        httpHeaders = new HttpHeaders();
        httpHeaders.add(Headers.AUTHORIZATION, "token123");
    }

    @Test
    @Order(1)
    void testCreateOrder() {

        createCustomer();
        createBook();

        order = new OrderDto();
        order.setCustomerId(customer.getId());
        order.setBookIdList(Collections.singletonList(book.getId()));
        order.setAmount(152D);
        order.setProcessDate(LocalDate.now());
        order.setStatus("OK");

        String url = "http://localhost:8092/order/v1/save";
        HttpEntity httpEntity = new HttpEntity(order, httpHeaders);
        ResponseEntity<OrderDto> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, OrderDto.class);
        order = response.getBody();
        Assertions.assertNotNull(response);
    }

    @Test
    @Order(2)
    void testFindById() {

        String url = "http://localhost:8092/order/v1/findByOrderId?orderId=" + order.getId();

        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<OrderDto> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, OrderDto.class);
        Assertions.assertNotNull(response);
    }

    @Test
    @Order(3)
    void testUpdate() {

        String url = "http://localhost:8092/order/update";

        HttpEntity httpEntity = new HttpEntity(order, httpHeaders);
        ResponseEntity<OrderDto> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, OrderDto.class);
        Assertions.assertNotNull(response);
    }

    @Test
    @Order(4)
    void testQueryByProcessDate() {

        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now().plusMonths(1);

        String url = "http://localhost:8092/order/v1/queryByProcessDate?startDate=" + startDate + "&endDate=" + endDate;

        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<OrderDto[]> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, OrderDto[].class);
        Assertions.assertNotNull(response);
    }

    @Test
    @Order(5)
    void testFindByCustomerId() {

        String url = "http://localhost:8092/order/v1/findByCustomerId?customerId" + order.getCustomerId();

        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<OrderDto[]> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, OrderDto[].class);
        Assertions.assertNotNull(response);
    }

    @Test
    @Order(6)
    void testGetTotalCount() {

        String url = "http://localhost:8092/order/v1/getTotalCount";

        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<Long> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Long.class);
        Assertions.assertNotNull(response);
    }

    @Test
    @Order(7)
    void testFindAll() {

        String url = "http://localhost:8092/order/v1/findAll";

        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<OrderDto[]> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, OrderDto[].class);
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
}
