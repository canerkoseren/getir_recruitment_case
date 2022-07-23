package com.example.order.rest.impl;

import com.example.order.rest.OrderController;
import com.example.order.service.OrderService;
import com.example.order.service.model.OrderDto;
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

@RestController
public class OrderControllerImpl implements OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderControllerImpl.class);

    private OrderService orderService;

    @Autowired
    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderDto> save(@RequestBody OrderDto orderDto) {

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
    public ResponseEntity<List<OrderDto>> queryByProcessDate(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<OrderDto> orderList = orderService.queryByProcessDate(startDate, endDate);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

}
