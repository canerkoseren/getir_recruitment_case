package com.example.order.data.dao;

import com.example.order.data.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository implementations for {@link Order}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public interface OrderRepository extends MongoRepository<Order, Long> {

    Order insert(Order order);

    @Override
    Order save(Order entity);

    @Override
    long count();

    @Override
    Optional<Order> findById(Long orderId);

    @Query("{'customerId' : ?0}")
    Optional<List<Order>> findByCustomerId(Long customerId);

    @Query("{'processDate' : { $gte: ?0, $lte: ?1 } }")
    Optional<List<Order>> findByProcessDateBetween(LocalDate startDate, LocalDate endDate);

    @Override
    List<Order> findAll();
}
