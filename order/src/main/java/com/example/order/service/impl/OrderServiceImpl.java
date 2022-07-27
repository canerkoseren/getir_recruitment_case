package com.example.order.service.impl;

import com.example.order.data.dao.OrderRepository;
import com.example.order.data.entity.Order;
import com.example.order.rest.configuration.client.RestClient;
import com.example.order.service.OrderService;
import com.example.order.service.model.BookDto;
import com.example.order.service.model.CustomerDto;
import com.example.order.service.model.OrderDto;
import com.example.order.service.model.exception.OrderProcessException;
import com.example.order.service.model.exception.OrderValidationException;
import com.example.order.service.model.mapper.OrderMapper;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service implementation of {@link OrderService}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private static final OrderMapper mapper = OrderMapper.INSTANCE;

    @Getter
    @Setter
    @Value("${order.app.findCustomerByIdUrl}")
    private String findCustomerByIdUrl;

    @Getter
    @Setter
    @Value("${order.app.findBookByIdUrl}")
    private String findBookByIdUrl;

    @Getter
    @Setter
    @Value("${order.app.bookUpdateUrl}")
    private String bookUpdateUrl;

    private OrderRepository orderRepository;
    private RestTemplate restTemplate;

    private Random random;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, RestClient restClient) {
        this.orderRepository = orderRepository;
        this.restTemplate = restClient.restTemplate();
        this.random = new Random();
    }

    @Override
    public OrderDto save(OrderDto order) throws OrderValidationException, OrderProcessException {

        checkCustomer(order.getCustomerId());

        updateBookStock(order.getBookIdList());

        Order orderEntity = mapper.mapToEntity(order);
        orderEntity.setId(getNextSequenceId());

        orderEntity = orderRepository.save(orderEntity);
        OrderDto orderDto = mapper.mapToDto(orderEntity);

        logger.info("Order - ID: {}, has been created.", orderDto.getId());

        return orderDto;

    }

    @Override
    public OrderDto findOrderById(Long orderId) {

        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(mapper::mapToDto).orElse(null);
    }

    @Override
    public OrderDto update(OrderDto order) {

        Order orderEntity = mapper.mapToEntity(order);
        orderEntity = orderRepository.save(orderEntity);

        OrderDto orderDto = mapper.mapToDto(orderEntity);

        logger.info("Order - ID: {}, has been updated.", orderDto.getId());

        return orderDto;
    }

    @Override
    public List<OrderDto> queryByProcessDate(LocalDate startDate, LocalDate endDate) throws OrderValidationException {

        if (startDate == null) {
            throw new OrderValidationException("Start date can not be empty");
        }

        if (endDate == null) {
            throw new OrderValidationException("End date can not be empty");
        }

        if (startDate.isAfter(endDate)) {
            throw new OrderValidationException("Start date can not be greater than end date.");
        }

        Optional<List<Order>> orderList = orderRepository.findByProcessDateBetween(startDate, endDate);
        return convertList(orderList);
    }

    @Override
    public List<OrderDto> findOrderByCustomerId(Long customerId) {

        Optional<List<Order>> orderList = orderRepository.findByCustomerId(customerId);
        return convertList(orderList);
    }

    @Override
    public Long getTotalCount() {
        return orderRepository.count();
    }

    @Override
    public List<OrderDto> findAll() {
        List<Order> orderList = orderRepository.findAll();
        return convertList(Optional.ofNullable(orderList));
    }

    private long getNextSequenceId() {
        return (random.nextLong() * (random.nextLong() % 100)) + orderRepository.count();
    }

    private List<OrderDto> convertList(Optional<List<Order>> orderList) {

        List<OrderDto> convertedList = new ArrayList<>();

        if (orderList.isPresent()) {
            Optional.ofNullable(orderList.get())
                    .ifPresent(orders -> orders.forEach(order -> convertedList.add(mapper.mapToDto(order))));
        }
        return convertedList;
    }

    private boolean checkCustomer(Long customerId) throws OrderProcessException, OrderValidationException {

        String url = findCustomerByIdUrl + customerId;

        try {
            CustomerDto customer = restTemplate.getForObject(url, CustomerDto.class);
            if (customer == null) {
                throw new OrderValidationException("Customer can not be found by customerId: " + customerId);
            }
            return true;
        } catch (HttpClientErrorException ex) {
            throw new OrderProcessException("HttpClientError while reading customer ", ex);
        }
    }

    private void updateBookStock(List<Long> bookIdList) throws OrderProcessException, OrderValidationException {

        if (bookIdList == null || bookIdList.isEmpty()) {
            throw new OrderValidationException("Book list is empty");
        }

        Map<Long, Long> bookQuantityMap = bookIdList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Set<Map.Entry<Long, Long>> entrySet = bookQuantityMap.entrySet();

        for (Map.Entry<Long, Long> entry : entrySet) {

            try {
                BookDto book = restTemplate.getForObject(findBookByIdUrl + entry.getKey(), BookDto.class);

                if (book == null) {
                    throw new OrderValidationException("Book not found");
                }

                long newStock = book.getStock() - entry.getValue();

                if (newStock < 0) {
                    throw new OrderValidationException("Negative number of stock");
                }

                book.setStock(newStock);
                restTemplate.put(bookUpdateUrl, book);

            } catch (HttpClientErrorException ex) {
                throw new OrderProcessException("HttpClientError while book operations ", ex);
            }
        }
    }
}
