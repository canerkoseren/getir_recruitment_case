package com.example.customer.rest.impl;

import com.example.customer.data.entity.Customer;
import com.example.customer.rest.CustomerController;
import com.example.customer.service.model.CustomerDto;
import com.example.customer.service.model.CustomerOrder;
import com.example.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerControllerImpl implements CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerControllerImpl.class);

    private CustomerService customerService;

    @Autowired
    public CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public void save(@RequestBody CustomerDto customerDto) {

        logger.info("Customer: {} will be saved", customerDto);
        customerService.save(customerDto);
    }

    @Override
    public ResponseEntity<CustomerDto> findCustomerById(@RequestParam Long customerId) {
        CustomerDto customer = customerService.findCustomerById(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerDto> findCustomerByEmail(String email) {
        CustomerDto customer = customerService.findCustomerByEmail(email);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CustomerOrder>> customerOrders(Long customerId) {
        return null;
    }
}
