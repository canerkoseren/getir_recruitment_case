package com.example.book.service.impl;

import com.example.book.data.dao.BookRepository;
import com.example.book.data.entity.Book;
import com.example.book.service.BookService;
import com.example.book.service.model.BookDto;
import com.example.book.service.model.exception.BookProcessException;
import com.example.book.service.model.exception.BookValidationException;
import com.example.book.service.model.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * Service implementations for {@link BookService}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    private static final BookMapper mapper = BookMapper.INSTANCE;

    private BookRepository bookRepository;
    private Random random;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.random = new Random();
    }

    @Override
    public BookDto save(BookDto book) throws BookValidationException, BookProcessException {

        if (Objects.isNull(book.getStock()) || book.getStock() <= 0) {
            throw new BookValidationException("Stock can not be less than 0");
        }
        if (Objects.isNull(book.getTitle()) || book.getTitle().isEmpty()) {
            throw new BookValidationException("Title can not be empty");
        }

        Optional<Book> bookByTitle = bookRepository.findByTitle(book.getTitle());

        if (bookByTitle.isPresent()) {
            throw new BookProcessException("Book [@id= " + bookByTitle.get().getId() + "] has the same title");
        }

        Book bookEntity = mapper.mapToEntity(book);
        bookEntity.setId(getNextSequenceId());

        bookEntity = bookRepository.insert(bookEntity);

        return mapper.mapToDto(bookEntity);
    }

    @Override
    public BookDto findBookById(Long bookId) throws BookValidationException, BookProcessException {

        if (bookId == 0) {
            throw new BookValidationException("bookId can not be empty");
        }

        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isEmpty()) {
            throw new BookProcessException("Book can not be found by id: " + bookId);
        }

        return mapper.mapToDto(book.get());
    }

    @Override
    public BookDto update(BookDto book) throws BookValidationException, BookProcessException {

        if (book.getId() == 0) {
            throw new BookValidationException("bookId can not be empty");
        }

        if (book.getStock() < 0) {
            throw new BookValidationException("Stock can not be less than 0");
        }

        findBookById(book.getId());

        Book bookEntity = mapper.mapToEntity(book);

        bookEntity = bookRepository.save(bookEntity);
        return mapper.mapToDto(bookEntity);
    }

    private long getNextSequenceId() {
        return (random.nextLong() * (random.nextLong() % 100)) + bookRepository.count();
    }
}
