package com.example.order.rest.impl;

import com.example.order.rest.OrderController;
import com.example.order.service.OrderService;
import com.example.order.service.model.OrderDto;
import com.example.order.service.model.exception.OrderProcessException;
import com.example.order.service.model.exception.OrderValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of {@link OrderController}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@RestController
public class OrderControllerImpl implements OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderControllerImpl.class);

    private OrderService orderService;

    @Autowired
    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderDto> save(@RequestBody OrderDto orderDto) throws OrderValidationException, OrderProcessException {

        logger.info("Order: {} will be saved", orderDto);
        OrderDto order = orderService.save(orderDto);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderDto> findOrderById(@RequestParam Long orderId) {
        OrderDto order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderDto> update(@RequestBody OrderDto orderDto) {
        OrderDto order = orderService.update(orderDto);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<OrderDto>> queryByProcessDate(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) throws OrderValidationException {
        List<OrderDto> orderList = orderService.queryByProcessDate(startDate, endDate);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<OrderDto>> findOrderByCustomerId(Long customerId) {
        List<OrderDto> orderList = orderService.findOrderByCustomerId(customerId);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Long> getTotalCount() {
        Long totalCount = orderService.getTotalCount();
        return new ResponseEntity<>(totalCount, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<OrderDto>> findAll() {
        List<OrderDto> allOrders = orderService.findAll();
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

}
