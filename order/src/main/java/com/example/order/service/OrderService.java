package com.example.order.service;

import com.example.order.service.model.OrderDto;
import com.example.order.service.model.exception.OrderProcessException;
import com.example.order.service.model.exception.OrderValidationException;

import java.time.LocalDate;
import java.util.List;

/**
 * Order service contracts.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public interface OrderService {

    OrderDto save(OrderDto order) throws OrderValidationException, OrderProcessException;

    OrderDto findOrderById(Long orderId);

    OrderDto update(OrderDto order);

    List<OrderDto> queryByProcessDate(LocalDate startDate, LocalDate endDate) throws OrderValidationException;

    List<OrderDto> findOrderByCustomerId(Long customerId);

    Long getTotalCount();

    List<OrderDto> findAll();
}
