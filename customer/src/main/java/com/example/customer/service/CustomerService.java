package com.example.customer.service;

/**
 * Service contracts for Customer.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */

import com.example.customer.service.model.CustomerDto;
import com.example.customer.service.model.exception.CustomerProcessException;
import com.example.customer.service.model.exception.CustomerValidationException;

/**
 * Customer service contracts.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public interface CustomerService {

    CustomerDto save(CustomerDto customer) throws CustomerValidationException, CustomerProcessException;

    CustomerDto findCustomerById(Long customerId) throws CustomerValidationException, CustomerProcessException;

    CustomerDto findCustomerByEmail(String email) throws CustomerValidationException, CustomerProcessException;

}
