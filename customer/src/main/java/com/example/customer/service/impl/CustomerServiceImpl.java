package com.example.customer.service.impl;

import com.example.customer.data.dao.CustomerRepository;
import com.example.customer.data.entity.Customer;
import com.example.customer.service.CustomerService;
import com.example.customer.service.model.CustomerDto;
import com.example.customer.service.model.exception.CustomerProcessException;
import com.example.customer.service.model.exception.CustomerValidationException;
import com.example.customer.service.model.mapper.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * Service implementations for {@link CustomerService}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private static final CustomerMapper mapper = CustomerMapper.INSTANCE;

    private CustomerRepository customerRepository;
    private Random random;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.random = new Random();
    }

    @Override
    public CustomerDto save(CustomerDto customer) throws CustomerValidationException {

        if (!validateCustomer(customer)) {
            throw new CustomerValidationException("Enter customer information");
        }

        try {
            CustomerDto customerByEmail = findCustomerByEmail(customer.getEmail());

            if (customerByEmail != null) {
                throw new CustomerValidationException("Enter different email. " +
                        "This email is being used by customer: " + customerByEmail.getId());
            }
        } catch (CustomerProcessException e) {
            logger.info("Email validations ok");
        }

        Customer customerEntity = mapper.mapToEntity(customer);
        customerEntity.setId(getNextSequenceId());

        customerEntity = customerRepository.insert(customerEntity);
        logger.debug("Customer: {} has been saved", customer);
        return mapper.mapToDto(customerEntity);
    }

    @Override
    public CustomerDto findCustomerById(Long customerId) throws CustomerValidationException, CustomerProcessException {

        if (Objects.isNull(customerId) || customerId == 0) {
            throw new CustomerValidationException("customerId cannot be 0");
        }

        Optional<Customer> customerEntity = customerRepository.findById(customerId);

        if (customerEntity.isEmpty()) {
            throw new CustomerProcessException("Customer can not be found by id");
        }

        return mapper.mapToDto(customerEntity.get());
    }

    @Override
    public CustomerDto findCustomerByEmail(String email) throws CustomerValidationException, CustomerProcessException {

        if (Objects.isNull(email) || email.isEmpty()) {
            throw new CustomerValidationException("email cannot be empty");
        }

        Optional<Customer> customerEntity = customerRepository.findByEmail(email);
        if (customerEntity.isEmpty()) {
            throw new CustomerProcessException("Customer can not be found by email");
        }
        return mapper.mapToDto(customerEntity.get());
    }

    private long getNextSequenceId() {
        return (random.nextLong() * (random.nextLong() % 100)) + customerRepository.count();
    }

    private boolean validateCustomer(CustomerDto customer) {

        if (Objects.isNull(customer)) {
            return false;
        }
        if (Objects.isNull(customer.getName()) || customer.getName().isEmpty()) {
            return false;
        }
        if (Objects.isNull(customer.getLastName()) || customer.getLastName().isEmpty()) {
            return false;
        }
        if (Objects.isNull(customer.getEmail()) || customer.getEmail().isEmpty()) {
            return false;
        }

        return true;
    }
}
