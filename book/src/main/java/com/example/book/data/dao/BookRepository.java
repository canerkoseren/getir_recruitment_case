package com.example.book.data.dao;

import com.example.book.data.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository implementations for {@link Book}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public interface BookRepository extends MongoRepository<Book, Long> {

    @Query("{title:'?0'}")
    Optional<Book> findByTitle(String title);

    Book insert(Book customer);

    @Override
    Book save(Book entity);

    @Override
    long count();

    @Override
    Optional<Book> findById(Long aLong);

    @Override
    List<Book> findAll();
}
