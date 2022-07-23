package com.example.customer.service;

import com.example.customer.service.model.CustomerDto;
import com.example.customer.service.model.CustomerOrder;

import java.util.List;

public interface CustomerService {

    void save(CustomerDto customer);

    List<CustomerOrder> customerOrders(Long customerId) throws Exception;
}
