package com.example.customer.rest;

import com.example.customer.service.model.CustomerDto;
import com.example.customer.service.model.exception.CustomerProcessException;
import com.example.customer.service.model.exception.CustomerValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller contracts.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@RequestMapping(value = "customer/v1")
public interface CustomerController {

    @GetMapping(value = "status")
    ResponseEntity<String> status();

    @PostMapping(value = "save")
    ResponseEntity<CustomerDto> save(@RequestBody CustomerDto customerDto) throws CustomerValidationException, CustomerProcessException;

    @GetMapping(value = "findById")
    ResponseEntity<CustomerDto> findCustomerById(@RequestParam(name = "customerId") Long customerId) throws CustomerValidationException, CustomerProcessException;

    @GetMapping(value = "findByEmail")
    ResponseEntity<CustomerDto> findCustomerByEmail(@RequestParam(name = "email") String email) throws CustomerValidationException, CustomerProcessException;
}
