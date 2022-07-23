package com.example.order.service.impl;

import com.example.order.data.dao.OrderDao;
import com.example.order.data.entity.Order;
import com.example.order.data.mapper.OrderMapper;
import com.example.order.service.OrderService;
import com.example.order.service.model.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private static final OrderMapper mapper = OrderMapper.INSTANCE;

    private OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }


    @Override
    public OrderDto save(OrderDto order) {

        Order orderEntity = mapper.mapToEntity(order);
        orderEntity.setId(getNextSequenceId());

        orderEntity = orderDao.save(orderEntity);

        return mapper.mapToDto(orderEntity);
    }

    @Override
    public OrderDto findOrderById(Long orderId) {

        Optional<Order> order = orderDao.findById(orderId);
        return mapper.mapToDto(order.get());
    }

    @Override
    public OrderDto update(OrderDto order) {

        Order orderEntity = mapper.mapToEntity(order);
        orderEntity = orderDao.save(orderEntity);

        return mapper.mapToDto(orderEntity);
    }

    @Override
    public List<OrderDto> queryByProcessDate(LocalDate startDate, LocalDate endDate) {

        Optional<List<Order>> orderList = orderDao.findByProcessDateBetween(startDate, endDate);
        return convertList(orderList);
    }

    @Override
    public List<OrderDto> findOrderByCustomerId(Long customerId) {

        Optional<List<Order>> orderList = orderDao.findByCustomerId(customerId);
        return convertList(orderList);
    }

    private long getNextSequenceId() {
        Random random = new Random();
        return (random.nextLong() * (random.nextLong() % 100)) + orderDao.count();
    }

    private List<OrderDto> convertList(Optional<List<Order>> orderList) {

        List<OrderDto> convertedList = new ArrayList<>();

        Optional.ofNullable(orderList.get()).ifPresent( orders -> orders.forEach(order -> convertedList.add(mapper.mapToDto(order))));
        return convertedList;
    }
}
