package com.example.customer.data.dao;

import com.example.customer.data.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerDao extends MongoRepository<Customer, Long> {

    Optional<Customer> findById(Long customerId);

    Customer insert(Customer customer);

    @Override
    long count();
}
