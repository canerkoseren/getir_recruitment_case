package com.example.order.service;

import com.example.order.service.model.OrderDto;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderDto save(OrderDto order);

    OrderDto findOrderById(Long orderId);

    OrderDto update(OrderDto order);

    List<OrderDto> queryByProcessDate(LocalDate startDate, LocalDate endDate);

    List<OrderDto> findOrderByCustomerId(Long customerId);
}
