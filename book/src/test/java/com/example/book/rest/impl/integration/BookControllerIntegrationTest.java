package com.example.book.rest.impl.integration;

import com.example.book.BookApplication;
import com.example.book.rest.configuration.constant.Headers;
import com.example.book.service.model.BookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = BookApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {

    private static HttpHeaders httpHeaders;
    private static BookDto book;
    private static String urlPrefix;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    static void setUp() {

        urlPrefix = "http://localhost:8091/book/v1";

        httpHeaders = new HttpHeaders();
        httpHeaders.add(Headers.AUTHORIZATION, "token123");

        book = new BookDto();
        book.setDescription("test-description");
        book.setTitle("test-title-" + LocalDateTime.now());
        book.setKind("test-kind");
        book.setStock(150l);
        book.setWriter("test-writer");
    }

    @Test
    @Order(1)
    void testStatus() {

        String url = urlPrefix + "/status";
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<BookDto> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, BookDto.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    void testSave() {

        String url = urlPrefix + "/save";
        HttpEntity httpEntity = new HttpEntity(book, httpHeaders);
        ResponseEntity<BookDto> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, BookDto.class);
        book = response.getBody();
        Assertions.assertNotNull(book);
    }

    @Test
    @Order(3)
    void testFindById() {

        String url = urlPrefix + "/findById?bookId=" + book.getId();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<BookDto> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, BookDto.class);
        book = response.getBody();
        Assertions.assertNotNull(book);
    }

    @Test
    @Order(4)
    void testUpdate() {

        String url = urlPrefix + "/update";
        book.setStock(book.getStock() + 15l);
        HttpEntity httpEntity = new HttpEntity(book, httpHeaders);
        ResponseEntity<BookDto> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, BookDto.class);
        book = response.getBody();
        Assertions.assertNotNull(book);
    }

    @Test
    @Order(5)
    void testStocks() {

        String url = urlPrefix + "/stocks";
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<List> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, List.class);
        Assertions.assertNotNull(exchange.getBody());
    }
}
