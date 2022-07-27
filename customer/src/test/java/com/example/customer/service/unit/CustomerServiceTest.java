package com.example.customer.service.unit;

import com.example.customer.data.dao.CustomerRepository;
import com.example.customer.data.entity.Customer;
import com.example.customer.service.CustomerService;
import com.example.customer.service.impl.CustomerServiceImpl;
import com.example.customer.service.model.CustomerDto;
import com.example.customer.service.model.exception.CustomerProcessException;
import com.example.customer.service.model.exception.CustomerValidationException;
import com.example.customer.service.model.mapper.CustomerMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test implementation for {@link CustomerService}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 27.7.2022
 */
class CustomerServiceTest {

    private static final CustomerMapper mapper = CustomerMapper.INSTANCE;

    private static CustomerDto invalidCustomer;
    private static CustomerDto existentCustomer;
    private static CustomerDto newCustomer;
    private static CustomerDto missingCustomer;

    private static CustomerService customerService;

    @BeforeAll
    static void setUp() {

        invalidCustomer = new CustomerDto();
        invalidCustomer.setName("invalid-customer-name");
        invalidCustomer.setLastName("invalid-customer-last-name");

        existentCustomer = new CustomerDto();
        existentCustomer.setId(111L);
        existentCustomer.setName("existent-customer-name");
        existentCustomer.setLastName("existent-customer-last-name");
        existentCustomer.setEmail("existent-customer-email");
        Customer existentCustomerEntity = mapper.mapToEntity(existentCustomer);

        newCustomer = new CustomerDto();
        newCustomer.setName("new-customer-name");
        newCustomer.setLastName("new-customer-last-name");
        newCustomer.setEmail("new-customer-email");
        Customer newCustomerEntity = mapper.mapToEntity(newCustomer);

        missingCustomer = new CustomerDto();
        missingCustomer.setId(678900099L);
        missingCustomer.setName("missing-customer-name");
        missingCustomer.setLastName("missing-customer-last-name");
        missingCustomer.setEmail("missing-customer-email");

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById(missingCustomer.getId())).thenReturn(Optional.empty());
        when(customerRepository.findById(existentCustomer.getId())).thenReturn(Optional.of(existentCustomerEntity));
        when(customerRepository.findByEmail(newCustomer.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.findByEmail(missingCustomer.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.findByEmail(existentCustomer.getEmail())).thenReturn(Optional.of(existentCustomerEntity));

        when(customerRepository.insert(any(Customer.class))).thenReturn(newCustomerEntity);

        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void when_EmailIsEmptyToSave_ThrowException() {

        CustomerValidationException exception = Assertions.assertThrows(CustomerValidationException.class, () -> {
            customerService.save(invalidCustomer);
        });
        Assertions.assertEquals(CustomerValidationException.class, exception.getClass());
    }

    @Test
    void when_EmailBelongsToAnotherCustomerToSave_ThrowException() {

        CustomerValidationException exception = Assertions.assertThrows(CustomerValidationException.class, () -> {
            customerService.save(existentCustomer);
        });
        Assertions.assertEquals(CustomerValidationException.class, exception.getClass());
    }

    @Test
    void when_AllValidationsAreOkToSave_ThrowException() throws CustomerValidationException, CustomerProcessException {

        CustomerDto customerDto = customerService.save(newCustomer);
        Assertions.assertNotNull(customerDto);
    }

    @Test
    void when_CustomerIdIsEmptyToFindById_ThrowException() {

        CustomerValidationException exception = Assertions.assertThrows(CustomerValidationException.class, () -> {
            customerService.findCustomerById(0L);
        });
        Assertions.assertEquals(CustomerValidationException.class, exception.getClass());
    }

    @Test
    void when_CustomerIsNotFoundById_ThrowException() {

        CustomerProcessException exception = Assertions.assertThrows(CustomerProcessException.class, () -> {
            customerService.findCustomerById(missingCustomer.getId());
        });
        Assertions.assertEquals(CustomerProcessException.class, exception.getClass());
    }

    @Test
    void when_CustomerIsFoundById_ReturnCustomer() throws CustomerValidationException, CustomerProcessException {

        CustomerDto customer = customerService.findCustomerById(existentCustomer.getId());
        Assertions.assertNotNull(customer);
    }

    @Test
    void when_CustomerEmailIsEmptyToFind_ThrowException() {

        CustomerValidationException exception = Assertions.assertThrows(CustomerValidationException.class, () -> {
            customerService.findCustomerByEmail("");
        });
        Assertions.assertEquals(CustomerValidationException.class, exception.getClass());
    }

    @Test
    void when_CustomerIsNotFoundByEmail_ThrowException() {

        CustomerProcessException exception = Assertions.assertThrows(CustomerProcessException.class, () -> {
            customerService.findCustomerByEmail(missingCustomer.getEmail());
        });
        Assertions.assertEquals(CustomerProcessException.class, exception.getClass());
    }

    @Test
    void when_CustomerIsFoundByEmail_ReturnCustomer() throws CustomerValidationException, CustomerProcessException {

        CustomerDto customer = customerService.findCustomerByEmail(existentCustomer.getEmail());
        Assertions.assertNotNull(customer);
    }
}
