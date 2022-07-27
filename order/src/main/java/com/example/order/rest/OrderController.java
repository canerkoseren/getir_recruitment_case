package com.example.order.rest;

import com.example.order.service.model.OrderDto;
import com.example.order.service.model.exception.OrderProcessException;
import com.example.order.service.model.exception.OrderValidationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller contracts.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@RequestMapping(value = "order/v1")
public interface OrderController {

    @PostMapping(value = "save")
    ResponseEntity<OrderDto> save(@RequestBody OrderDto orderDto) throws OrderValidationException, OrderProcessException;

    @GetMapping(value = "findById")
    ResponseEntity<OrderDto> findOrderById(@RequestParam(name = "orderId") Long orderId);

    @PutMapping(value = "update")
    ResponseEntity<OrderDto> update(@RequestBody OrderDto orderDto);

    @GetMapping(value = "queryByProcessDate")
    ResponseEntity<List<OrderDto>> queryByProcessDate(
            @RequestParam(name = "startDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws OrderValidationException;

    @GetMapping(value = "findByCustomerId")
    ResponseEntity<List<OrderDto>> findOrderByCustomerId(@RequestParam(name = "customerId") Long customerId);

    @GetMapping(value = "getTotalCount")
    ResponseEntity<Long> getTotalCount();

    @GetMapping(value = "findAll")
    ResponseEntity<List<OrderDto>> findAll();
}
