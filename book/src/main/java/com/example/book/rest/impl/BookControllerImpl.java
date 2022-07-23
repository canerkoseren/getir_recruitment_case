package com.example.book.rest.impl;

import com.example.book.rest.BookController;
import com.example.book.service.BookService;
import com.example.book.service.model.BookDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookControllerImpl implements BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookControllerImpl.class);

    private BookService bookService;

    @Autowired
    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BookDto> save(@RequestBody BookDto bookDto) {

        logger.info("Book: {} will be saved", bookDto);
        BookDto book = bookService.save(bookDto);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BookDto> findBookById(@RequestParam Long bookId) {
        BookDto book = bookService.findBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BookDto> update(@RequestBody BookDto bookDto) {
        BookDto book = bookService.update(bookDto);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

}
