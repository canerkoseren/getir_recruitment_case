package com.example.book.rest;

import com.example.book.service.model.BookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "app/v1/book")
public interface BookController {

    @GetMapping(value = "status")
    ResponseEntity<String> status();

    @PostMapping(value = "save")
    ResponseEntity<BookDto> save(@RequestBody BookDto bookDto);

    @GetMapping(value = "findById")
    ResponseEntity<BookDto> findBookById(@RequestParam(name = "bookId") Long bookId);

    @PutMapping(value = "update")
    ResponseEntity<BookDto> update(@RequestBody BookDto bookDto);
}
