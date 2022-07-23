package com.example.book.data.dao;

import com.example.book.data.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface BookDao extends MongoRepository<Book, Long> {

    @Query("{title:'?0'}")
    Optional<Book> findByTitle(String title);

    Book insert(Book customer);

    @Override
    Book save(Book entity);

    @Override
    long count();

    @Override
    Optional<Book> findById(Long aLong);

}
