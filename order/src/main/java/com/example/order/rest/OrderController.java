package com.example.order.rest;

import com.example.order.service.model.OrderDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping(value = "app/v1/order")
public interface OrderController {

    @GetMapping(value = "status")
    ResponseEntity<String> status();

    @PostMapping(value = "save")
    ResponseEntity<OrderDto> save(@RequestBody OrderDto orderDto);

    @GetMapping(value = "findById")
    ResponseEntity<OrderDto> findOrderById(@RequestParam(name = "orderId") Long orderId);

    @PutMapping(value = "update")
    ResponseEntity<OrderDto> update(@RequestBody OrderDto orderDto);

    @GetMapping(value = "queryByProcessDate")
    ResponseEntity<List<OrderDto>> queryByProcessDate(
            @RequestParam(name = "startDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate);
}
