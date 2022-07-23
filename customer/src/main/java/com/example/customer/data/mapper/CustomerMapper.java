package com.example.customer.data.mapper;

import com.example.customer.data.entity.Customer;
import com.example.customer.service.model.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class CustomerMapper {

    public static final CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    public abstract Customer mapToEntity(CustomerDto customer);

    public abstract CustomerDto mapToDto(Customer customer);
}
