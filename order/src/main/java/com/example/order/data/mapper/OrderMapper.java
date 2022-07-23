package com.example.order.data.mapper;

import com.example.order.data.entity.Order;
import com.example.order.service.model.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class OrderMapper {

    public static final OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    public abstract Order mapToEntity(OrderDto order);

    public abstract OrderDto mapToDto(Order order);
}
