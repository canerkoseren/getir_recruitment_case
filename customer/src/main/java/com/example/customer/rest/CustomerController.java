package com.example.customer.rest;

import com.example.customer.service.model.CustomerDto;
import com.example.customer.service.model.CustomerOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(value = "app/v1/customer")
public interface CustomerController {

    @GetMapping(value = "status")
    ResponseEntity<String> status();

    @PostMapping(value = "save")
    void save(@RequestBody CustomerDto customerDto);

    @GetMapping(value = "customerOrders")
    ResponseEntity<List<CustomerOrder>> customerOrders(@RequestParam(name = "customerId") Long customerId);
}
