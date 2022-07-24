package com.example.order.service.model.mapper;

import com.example.order.data.entity.Order;
import com.example.order.service.model.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper implementation between {@link OrderDto} and {@link Order}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@Mapper
public abstract class OrderMapper {

    public static final OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    public abstract Order mapToEntity(OrderDto order);

    public abstract OrderDto mapToDto(Order order);
}
