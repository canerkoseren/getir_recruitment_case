package com.example.book.service.unit;

import com.example.book.data.dao.BookRepository;
import com.example.book.data.entity.Book;
import com.example.book.service.BookService;
import com.example.book.service.impl.BookServiceImpl;
import com.example.book.service.model.BookDto;
import com.example.book.service.model.BookStockDto;
import com.example.book.service.model.exception.BookProcessException;
import com.example.book.service.model.exception.BookValidationException;
import com.example.book.service.model.mapper.BookMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test implementation for {@link BookService}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 27.7.2022
 */
class BookServiceTest {

    private static final String NEW_BOOK_TITLE = "new-book-title";
    private static final Long EXISTENT_BOOK_ID = 998L;
    private static final String EXISTENT_BOOK_TITLE = "existent-book-title";
    private static final Long NOT_EXISTENT_BOOK_ID = 999L;

    private static final BookMapper mapper = BookMapper.INSTANCE;

    private static BookRepository bookRepository;
    private static BookService bookService;
    private static BookDto newBook;
    private static BookDto existentBook;

    @BeforeAll
    static void setUp() {

        newBook = new BookDto();
        newBook.setTitle(NEW_BOOK_TITLE);
        newBook.setDescription("description");
        newBook.setKind("kind");
        newBook.setWriter("writer");
        newBook.setStock(99L);

        existentBook = new BookDto();
        existentBook.setId(EXISTENT_BOOK_ID);
        existentBook.setTitle(EXISTENT_BOOK_TITLE);
        existentBook.setDescription("description");
        existentBook.setKind("kind");
        existentBook.setWriter("writer");
        existentBook.setStock(99L);

        Optional<Book> bookOptional = Optional.of(mapper.mapToEntity(existentBook));

        Book insetedBookEntity = mapper.mapToEntity(newBook);
        Book updatedBookEntity = mapper.mapToEntity(existentBook);

        bookRepository = mock(BookRepository.class);

        when(bookRepository.insert(any(Book.class))).thenReturn(insetedBookEntity);
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBookEntity);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(updatedBookEntity));
        when(bookRepository.findById(existentBook.getId())).thenReturn(bookOptional);
        when(bookRepository.findById(NOT_EXISTENT_BOOK_ID)).thenReturn(Optional.empty());
        when(bookRepository.findByTitle(newBook.getTitle())).thenReturn(Optional.empty());
        when(bookRepository.findByTitle(existentBook.getTitle())).thenReturn(bookOptional);

        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    void when_BookStockIsZero_ThrowException() {

        BookDto book = new BookDto();
        book.setStock(0L);

        BookValidationException exception = Assertions.assertThrows(BookValidationException.class, () -> {
            bookService.save(book);
        });
        Assertions.assertEquals(BookValidationException.class, exception.getClass());
    }

    @Test
    void when_BookTitleIsEmpty_ThrowException() {

        BookDto book = new BookDto();
        book.setStock(1L);

        BookValidationException exception = Assertions.assertThrows(BookValidationException.class, () -> {
            bookService.save(book);
        });
        Assertions.assertEquals(BookValidationException.class, exception.getClass());
    }

    @Test
    void when_BookTitleHasAlreadyBeenSaved_ThrowException() {

        BookProcessException exception = Assertions.assertThrows(BookProcessException.class, () -> {
            bookService.save(existentBook);
        });
        Assertions.assertEquals(BookProcessException.class, exception.getClass());
    }

    @Test
    void when_AllValidationsAreOk_InsertNewBook() throws BookProcessException, BookValidationException {

        BookDto book = bookService.save(newBook);
        Assertions.assertNotNull(book);
        verify(bookRepository, atLeastOnce()).insert(any(Book.class));
    }

    @Test
    void when_BookIdIsZeroToFindById_ThrowException() {

        BookValidationException exception = Assertions.assertThrows(BookValidationException.class, () -> {
            bookService.findBookById(0L);
        });
        Assertions.assertEquals(BookValidationException.class, exception.getClass());
    }

    @Test
    void when_BookIdIsNotMatchedARecord_ReturnBook(){

        BookProcessException exception = Assertions.assertThrows(BookProcessException.class, () -> {
            bookService.findBookById(NOT_EXISTENT_BOOK_ID);
        });
        Assertions.assertEquals(BookProcessException.class, exception.getClass());
    }

    @Test
    void when_BookIdIsMatchedARecord_ReturnBook() throws BookProcessException, BookValidationException {

        BookDto book = bookService.findBookById(EXISTENT_BOOK_ID);
        Assertions.assertNotNull(book);
        Assertions.assertEquals(EXISTENT_BOOK_ID, book.getId());
        Assertions.assertEquals(existentBook.getId(), book.getId());
    }

    @Test
    void when_BookIdIsZeroToUpdated_ThrowException() {

        BookValidationException exception = Assertions.assertThrows(BookValidationException.class, () -> {
            bookService.update(newBook);
        });
        Assertions.assertEquals(BookValidationException.class, exception.getClass());
    }

    @Test
    void when_BookStockIsZeroToUpdated_ThrowException() {

        BookValidationException exception = Assertions.assertThrows(BookValidationException.class, () -> {
            BookDto bookDto = new BookDto();
            bookDto.setId(980L);
            bookService.update(bookDto);
        });
        Assertions.assertEquals(BookValidationException.class, exception.getClass());
    }

    @Test
    void when_BookTitleIsEmptyToUpdated_ThrowException() {

        BookValidationException exception = Assertions.assertThrows(BookValidationException.class, () -> {
            BookDto bookDto = new BookDto();
            bookDto.setId(980L);
            bookDto.setStock(9L);
            bookService.update(bookDto);
        });
        Assertions.assertEquals(BookValidationException.class, exception.getClass());

        exception = Assertions.assertThrows(BookValidationException.class, () -> {
            BookDto bookDto = new BookDto();
            bookDto.setId(980L);
            bookDto.setStock(9L);
            bookDto.setTitle("");
            bookService.update(bookDto);
        });
        Assertions.assertEquals(BookValidationException.class, exception.getClass());
    }

    @Test
    void when_ValidationsAreOkToUpdated_UpdateBook() throws BookProcessException, BookValidationException {

        BookDto book = bookService.update(existentBook);
        Assertions.assertNotNull(book);
    }

    @Test
    void test_ListAllStocks(){

        List<BookStockDto> stocks = bookService.stocks();
        Assertions.assertNotNull(stocks);
    }
}
