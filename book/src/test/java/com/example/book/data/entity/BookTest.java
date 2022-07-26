package com.example.book.data.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookTest {

    private Book book;

    @BeforeEach
    public void setUp() {

        book = new Book();
        book.setId(1l);
        book.setDescription("description");
        book.setKind("kind");
        book.setStock(15l);
        book.setTitle("title");
        book.setWriter("writer");
    }

    @Test
    void testGetters() {

        Assertions.assertEquals(1l, book.getId());
        Assertions.assertNotNull(book.getDescription());
        Assertions.assertNotNull(book.getKind());
        Assertions.assertEquals(15l, book.getStock());
        Assertions.assertNotNull(book.getTitle());
        Assertions.assertNotNull(book.getWriter());
    }
}
