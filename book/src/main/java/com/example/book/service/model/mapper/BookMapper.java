package com.example.book.service.model.mapper;

import com.example.book.data.entity.Book;
import com.example.book.service.model.BookDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper implementation between {@link BookDto} and {@link Book}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@Mapper
public abstract class BookMapper {

    public static final BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    public abstract Book mapToEntity(BookDto book);

    public abstract BookDto mapToDto(Book book);
}
