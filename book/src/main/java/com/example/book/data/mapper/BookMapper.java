package com.example.book.data.mapper;

import com.example.book.data.entity.Book;
import com.example.book.service.model.BookDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class BookMapper {

    public static final BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    public abstract Book mapToEntity(BookDto book);

    public abstract BookDto mapToDto(Book book);
}
