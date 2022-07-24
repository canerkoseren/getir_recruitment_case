package com.example.book.service;

import com.example.book.service.model.BookDto;
import com.example.book.service.model.exception.BookProcessException;
import com.example.book.service.model.exception.BookValidationException;

/**
 * Service contracts.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public interface BookService {

    BookDto save(BookDto book) throws BookValidationException, BookProcessException;

    BookDto findBookById(Long bookId) throws BookValidationException, BookProcessException;

    BookDto update(BookDto book) throws Exception;
}
