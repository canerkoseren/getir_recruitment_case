package com.example.customer.rest.impl;

import com.example.customer.rest.CustomerController;
import com.example.customer.service.CustomerService;
import com.example.customer.service.model.CustomerDto;
import com.example.customer.service.model.exception.CustomerProcessException;
import com.example.customer.service.model.exception.CustomerValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of {@link CustomerController}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@RestController
public class CustomerControllerImpl implements CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerControllerImpl.class);

    private CustomerService customerService;

    @Autowired
    public CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<CustomerDto> save(@RequestBody CustomerDto customerDto) throws CustomerValidationException, CustomerProcessException {
        CustomerDto customer = customerService.save(customerDto);
        logger.info("Customer: {} has been saved", customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerDto> findCustomerById(@RequestParam Long customerId) throws CustomerValidationException, CustomerProcessException {
        CustomerDto customer = customerService.findCustomerById(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerDto> findCustomerByEmail(String email) throws CustomerValidationException, CustomerProcessException {
        CustomerDto customer = customerService.findCustomerByEmail(email);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
