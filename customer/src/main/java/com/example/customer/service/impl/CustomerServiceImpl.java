package com.example.customer.service.impl;

import com.example.customer.data.dao.CustomerDao;
import com.example.customer.data.entity.Customer;
import com.example.customer.data.mapper.CustomerMapper;
import com.example.customer.service.CustomerService;
import com.example.customer.service.model.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private static final CustomerMapper mapper = CustomerMapper.INSTANCE;

    private CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void save(CustomerDto customer) {

        long count = customerDao.count();
        Customer customerEntity = mapper.mapToEntity(customer);

        customerDao.insert(customerEntity);
        logger.debug("Customer: {} has been saved", customer);
    }

    @Override
    public ArrayList customerOrders(Long customerId) throws Exception {

        return Optional.ofNullable(customerDao.findById(customerId)).map(customer -> new ArrayList()).orElseThrow(() -> new Exception("Invalid customerId"));
    }
}
