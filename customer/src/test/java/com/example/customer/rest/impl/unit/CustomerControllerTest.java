package com.example.customer.rest.impl.unit;

import com.example.customer.rest.CustomerController;
import com.example.customer.rest.impl.CustomerControllerImpl;
import com.example.customer.service.CustomerService;
import com.example.customer.service.model.CustomerDto;
import com.example.customer.service.model.exception.CustomerProcessException;
import com.example.customer.service.model.exception.CustomerValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test implementation of {link CustomerController}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 27.7.2022
 */
class CustomerControllerTest {

    private static CustomerController customerController;

    @BeforeAll
    static void setUp() throws CustomerValidationException, CustomerProcessException {

        CustomerDto customer = mock(CustomerDto.class);

        CustomerService customerService = mock(CustomerService.class);
        when(customerService.save(any(CustomerDto.class))).thenReturn(customer);
        when(customerService.findCustomerById(anyLong())).thenReturn(customer);
        when(customerService.findCustomerByEmail(anyString())).thenReturn(customer);

        customerController = new CustomerControllerImpl(customerService);
    }

    @Test
    void testSave() throws CustomerValidationException, CustomerProcessException {

        ResponseEntity<CustomerDto> responseEntity = customerController.save(new CustomerDto());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Test
    void testFindById() throws CustomerValidationException, CustomerProcessException {

        ResponseEntity<CustomerDto> responseEntity = customerController.findCustomerById(987L);
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Test
    void testFindByEmail() throws CustomerValidationException, CustomerProcessException {

        ResponseEntity<CustomerDto> responseEntity = customerController.findCustomerByEmail("email");
        Assertions.assertNotNull(responseEntity.getBody());
    }
}
