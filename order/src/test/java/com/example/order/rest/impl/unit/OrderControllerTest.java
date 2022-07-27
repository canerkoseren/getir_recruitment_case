package com.example.order.rest.impl.unit;

import com.example.order.rest.OrderController;
import com.example.order.rest.impl.OrderControllerImpl;
import com.example.order.service.OrderService;
import com.example.order.service.model.OrderDto;
import com.example.order.service.model.exception.OrderProcessException;
import com.example.order.service.model.exception.OrderValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test implementation for {@link OrderController}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 28.7.2022
 */
public class OrderControllerTest {

    private static OrderService orderService;
    private static OrderController orderController;

    @BeforeAll
    static void setUp() throws OrderValidationException, OrderProcessException {

        orderService = mock(OrderService.class);
        when(orderService.save(any(OrderDto.class))).thenReturn(mock(OrderDto.class));
        when(orderService.findOrderById(anyLong())).thenReturn(mock(OrderDto.class));
        when(orderService.update(any(OrderDto.class))).thenReturn(mock(OrderDto.class));
        when(orderService.queryByProcessDate(any(), any())).thenReturn(Collections.singletonList(mock(OrderDto.class)));
        when(orderService.getTotalCount()).thenReturn(100L);
        when(orderService.findAll()).thenReturn(Collections.singletonList(mock(OrderDto.class)));

        orderController = new OrderControllerImpl(orderService);
    }

    @Test
    void testSave() throws OrderValidationException, OrderProcessException {

        ResponseEntity<OrderDto> response = orderController.save(mock(OrderDto.class));
        Assertions.assertNotNull(response);
    }

    @Test
    void testFindOrderById() {

        ResponseEntity<OrderDto> response = orderController.findOrderById(1L);
        Assertions.assertNotNull(response);
    }

    @Test
    void testUpdate() {

        ResponseEntity<OrderDto> response = orderController.update(mock(OrderDto.class));
        Assertions.assertNotNull(response);
    }

    @Test
    void testQueryByProcessDate() throws OrderValidationException {

        ResponseEntity<List<OrderDto>> response = orderController.queryByProcessDate(LocalDate.now(), LocalDate.now());
        Assertions.assertNotNull(response);
    }

    @Test
    void testGetTotalCount(){

        ResponseEntity<Long> response = orderController.getTotalCount();
        Assertions.assertNotNull(response);
    }

    @Test
    void testFindAll(){

        ResponseEntity<List<OrderDto>> response = orderController.findAll();
        Assertions.assertNotNull(response);
    }
}
