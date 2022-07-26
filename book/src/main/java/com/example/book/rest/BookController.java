package com.example.book.rest;

import com.example.book.service.model.BookDto;
import com.example.book.service.model.exception.BookProcessException;
import com.example.book.service.model.exception.BookValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "book/v1")
public interface BookController {

    @GetMapping(value = "status")
    ResponseEntity<String> status();

    @PostMapping(value = "save")
    ResponseEntity<BookDto> save(@RequestBody BookDto bookDto) throws BookValidationException, BookProcessException;

    @GetMapping(value = "findById")
    ResponseEntity<BookDto> findBookById(@RequestParam(name = "bookId") Long bookId) throws BookProcessException, BookValidationException;

    @PutMapping(value = "update")
    ResponseEntity<BookDto> update(@RequestBody BookDto bookDto) throws BookProcessException, BookValidationException;
}
