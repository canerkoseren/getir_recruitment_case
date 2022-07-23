package com.example.book.service.impl;

import com.example.book.data.dao.BookDao;
import com.example.book.data.entity.Book;
import com.example.book.data.mapper.BookMapper;
import com.example.book.service.BookService;
import com.example.book.service.model.BookDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    private static final BookMapper mapper = BookMapper.INSTANCE;

    private BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }


    @Override
    public BookDto save(BookDto book) {

        Book bookEntity = mapper.mapToEntity(book);
        bookEntity.setId(getNextSequenceId());

        bookEntity = bookDao.save(bookEntity);

        return mapper.mapToDto(bookEntity);
    }

    @Override
    public BookDto findBookById(Long bookId) {

        Optional<Book> book = bookDao.findById(bookId);
        return mapper.mapToDto(book.get());
    }

    @Override
    public BookDto update(BookDto book) {

        Book bookEntity = mapper.mapToEntity(book);
        bookEntity = bookDao.save(bookEntity);

        return mapper.mapToDto(bookEntity);
    }

    private long getNextSequenceId() {
        Random random = new Random();
        return (random.nextLong() * (random.nextLong() % 100)) + bookDao.count();
    }
}
