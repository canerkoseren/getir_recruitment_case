package com.example.order.data.dao;

import com.example.order.data.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderDao extends MongoRepository<Order, Long> {

    Order insert(Order order);

    @Override
    Order save(Order entity);

    @Override
    long count();

    @Override
    Optional<Order> findById(Long orderId);

    @Query("{customerId:'?0'}")
    Optional<List<Order>> findByCustomerId(Long customerId);

    @Query("{'processDate' : { $gte: ?0, $lte: ?1 } }")
    Optional<List<Order>> findByProcessDateBetween(LocalDate startDate, LocalDate endDate);
}
