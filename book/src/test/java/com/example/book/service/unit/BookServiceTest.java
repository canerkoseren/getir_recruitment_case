package com.example.book.service.unit;

import com.example.book.data.dao.BookRepository;
import com.example.book.data.entity.Book;
import com.example.book.service.BookService;
import com.example.book.service.impl.BookServiceImpl;
import com.example.book.service.model.BookDto;
import com.example.book.service.model.exception.BookProcessException;
import com.example.book.service.model.exception.BookValidationException;
import com.example.book.service.model.mapper.BookMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
    private static final Long EXISTENT_BOOK_ID = 998l;
    private static final String EXISTENT_BOOK_TITLE = "existent-book-title";

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
        newBook.setStock(99l);

        existentBook = new BookDto();
        existentBook.setId(EXISTENT_BOOK_ID);
        existentBook.setTitle(EXISTENT_BOOK_TITLE);
        existentBook.setDescription("description");
        existentBook.setKind("kind");
        existentBook.setWriter("writer");
        existentBook.setStock(99l);

        Optional<Book> bookOptional = Optional.of(mapper.mapToEntity(existentBook));

        Book insertedBookEntity = mapper.mapToEntity(newBook);
        insertedBookEntity.setId(789l);

        bookRepository = mock(BookRepository.class);

        when(bookRepository.findByTitle(newBook.getTitle())).thenReturn(Optional.empty());
        when(bookRepository.insert(any(Book.class))).thenReturn(insertedBookEntity);

        when(bookRepository.findById(existentBook.getId())).thenReturn(bookOptional);
        when(bookRepository.findByTitle(existentBook.getTitle())).thenReturn(bookOptional);

        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    void when_BookStockIsZero_ThrowException() {

        BookDto book = new BookDto();
        book.setStock(0l);

        BookValidationException exception = Assertions.assertThrows(BookValidationException.class, () -> {
            bookService.save(book);
        });
        Assertions.assertEquals(BookValidationException.class, exception.getClass());
    }

    @Test
    void when_BookTitleIsEmpty_ThrowException() {

        BookDto book = new BookDto();
        book.setStock(1l);

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
}
