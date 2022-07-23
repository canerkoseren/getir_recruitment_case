package com.example.customer.data.dao;

import com.example.customer.data.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CustomerDao extends MongoRepository<Customer, Long> {

    @Query("{email:'?0'}")
    Optional<Customer> findByEmail(String email);

    Customer insert(Customer customer);

    @Override
    long count();

    @Override
    Optional<Customer> findById(Long aLong);

}
