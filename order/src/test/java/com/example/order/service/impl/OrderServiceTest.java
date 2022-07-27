package com.example.order.service.impl;

import com.example.order.data.dao.OrderRepository;
import com.example.order.data.entity.Order;
import com.example.order.rest.configuration.client.RestClient;
import com.example.order.service.OrderService;
import com.example.order.service.model.BookDto;
import com.example.order.service.model.CustomerDto;
import com.example.order.service.model.OrderDto;
import com.example.order.service.model.exception.OrderProcessException;
import com.example.order.service.model.exception.OrderValidationException;
import com.example.order.service.model.mapper.OrderMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link OrderService}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 28.7.2022
 */
class OrderServiceTest {

    private static final OrderMapper mapper = OrderMapper.INSTANCE;

    private static final Long CUSTOMER_ID = 999L;
    private static final String FIND_CUSTOMER_BY_ID_URL = "findCustomerByIdUrl";
    private static final Long BOOK_ID = 999L;
    private static final String FIND_BOOK_BY_ID_URL = "findBookByIdUrl";
    private static final String BOOK_UPDATE_URL = "bookUpdateUrl";

    private OrderRepository orderRepository;
    private RestClient restClient;

    private CustomerDto customer;
    private BookDto book;

    private OrderDto order;

    private OrderService orderService;

    @BeforeEach
    void set() {

        customer = new CustomerDto();
        customer.setId(CUSTOMER_ID);

        book = new BookDto();
        book.setId(BOOK_ID);
        book.setStock(5L);

        order = new OrderDto();
        order.setId(1L);
        order.setCustomerId(customer.getId());
        order.setBookIdList(Collections.singletonList(book.getId()));

        Order entity = mapper.mapToEntity(this.order);

        orderRepository = mock(OrderRepository.class);
        when(orderRepository.save(any())).thenReturn(entity);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(orderRepository.findByProcessDateBetween(any(), any())).thenReturn(Optional.of(Collections.singletonList(entity)));
        when(orderRepository.findByCustomerId(anyLong())).thenReturn(Optional.of(Collections.singletonList(entity)));
        when(orderRepository.count()).thenReturn(5L);

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForObject(eq(FIND_CUSTOMER_BY_ID_URL + CUSTOMER_ID), eq(CustomerDto.class))).thenReturn(customer);
        when(restTemplate.getForObject(eq(FIND_BOOK_BY_ID_URL + BOOK_ID), eq(BookDto.class))).thenReturn(book);
        doNothing().when(restTemplate).put(eq(BOOK_UPDATE_URL), any(BookDto.class));

        restClient = mock(RestClient.class);
        when(restClient.restTemplate()).thenReturn(restTemplate);

        orderService = new OrderServiceImpl(orderRepository, restClient);
        ReflectionTestUtils.setField(orderService, "findCustomerByIdUrl", FIND_CUSTOMER_BY_ID_URL);
        ReflectionTestUtils.setField(orderService, "findBookByIdUrl", FIND_BOOK_BY_ID_URL);
        ReflectionTestUtils.setField(orderService, "bookUpdateUrl", BOOK_UPDATE_URL);

    }

    @Test
    void testSave() throws OrderValidationException, OrderProcessException {

        OrderDto result = orderService.save(order);
        Assertions.assertNotNull(result);
    }

    @Test
    void testFindById() {

        OrderDto orderDto = orderService.findOrderById(0L);
        Assertions.assertNotNull(orderDto);
    }

    @Test
    void testUpdate() {

        OrderDto orderDto = orderService.update(order);
        Assertions.assertNotNull(orderDto);
    }

    @Test
    void when_StartDateIsNull_HandleException() {

        OrderValidationException exception = Assertions.assertThrows(OrderValidationException.class, () -> {
            orderService.queryByProcessDate(null, LocalDate.MAX);
        });
        Assertions.assertSame(OrderValidationException.class, exception.getClass());
    }

    @Test
    void when_EndDateIsNull_HandleException() {

        OrderValidationException exception = Assertions.assertThrows(OrderValidationException.class, () -> {
            orderService.queryByProcessDate(LocalDate.now(), null);
        });
        Assertions.assertSame(OrderValidationException.class, exception.getClass());
    }

    @Test
    void when_StartDate_gt_EndDate_HandleException() {

        OrderValidationException exception = Assertions.assertThrows(OrderValidationException.class, () -> {
            orderService.queryByProcessDate(LocalDate.MAX, LocalDate.MIN);
        });
        Assertions.assertSame(OrderValidationException.class, exception.getClass());
    }

    @Test
    void testFindByProcessDateBetween() throws OrderValidationException {

        List<OrderDto> orders = orderService.queryByProcessDate(LocalDate.now(), LocalDate.now());
        Assertions.assertNotNull(orders);
    }

    @Test
    void testFindByCustomerId() {

        List<OrderDto> orders = orderService.findOrderByCustomerId(CUSTOMER_ID);
        Assertions.assertNotNull(orders);
    }

    @Test
    void testTotalCount() {

        Long totalCount = orderService.getTotalCount();
        Assertions.assertNotNull(totalCount);
    }
}
