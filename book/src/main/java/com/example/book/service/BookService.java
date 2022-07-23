package com.example.book.service;

import com.example.book.service.model.BookDto;

public interface BookService {

    BookDto save(BookDto book);

    BookDto findBookById(Long bookId);

    BookDto update(BookDto book);
}
