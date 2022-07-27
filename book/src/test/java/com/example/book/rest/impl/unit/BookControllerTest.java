package com.example.book.rest.impl.unit;

import com.example.book.rest.BookController;
import com.example.book.rest.impl.BookControllerImpl;
import com.example.book.service.BookService;
import com.example.book.service.model.BookDto;
import com.example.book.service.model.BookStockDto;
import com.example.book.service.model.exception.BookProcessException;
import com.example.book.service.model.exception.BookValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test  implementation for {@link BookController}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 27.7.2022
 */
class BookControllerTest {

    private static BookController bookController;

    @BeforeAll
    static void setUp() throws Exception {

        BookDto bookDto = mock(BookDto.class);
        BookService bookService = mock(BookService.class);
        when(bookService.save(any())).thenReturn(bookDto);
        when(bookService.findBookById(anyLong())).thenReturn(bookDto);
        when(bookService.update(any())).thenReturn(bookDto);
        when(bookService.stocks()).thenReturn(List.of(new BookStockDto(0L, "title", 9L)));

        bookController = new BookControllerImpl(bookService);
    }

    @Test
    void testStatus() {
        ResponseEntity<String> response = bookController.status();
        Assertions.assertNotNull(response);
    }

    @Test
    void testSave() throws BookProcessException, BookValidationException {

        ResponseEntity<BookDto> response = bookController.save(mock(BookDto.class));
        Assertions.assertNotNull(response);
    }

    @Test
    void testUpdate() throws BookProcessException, BookValidationException {

        ResponseEntity<BookDto> response = bookController.update(mock(BookDto.class));
        Assertions.assertNotNull(response);
    }

    @Test
    void testStock() {
        ResponseEntity<List<BookStockDto>> stocks = bookController.stocks();
        Assertions.assertNotNull(stocks);
    }
}
