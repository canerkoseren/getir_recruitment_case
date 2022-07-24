package com.example.customer.service.model.mapper;

import com.example.customer.data.entity.Customer;
import com.example.customer.service.model.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper implementation between {@link CustomerDto} and {@link Customer}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@Mapper
public abstract class CustomerMapper {

    public static final CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    public abstract Customer mapToEntity(CustomerDto customer);

    public abstract CustomerDto mapToDto(Customer customer);
}
