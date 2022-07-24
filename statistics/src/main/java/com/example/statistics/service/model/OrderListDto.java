package com.example.statistics.service.model;

import com.example.statistics.service.model.OrderDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Order dto class.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public class OrderListDto implements Serializable {

    private List<OrderDto> orders;

    public OrderListDto() {
        this.orders = new ArrayList<>();
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }
}
