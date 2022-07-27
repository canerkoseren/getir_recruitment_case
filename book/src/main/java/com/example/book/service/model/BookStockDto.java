package com.example.book.service.model;

/**
 * Book stock dto class.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public class BookStockDto {

    private Long bookId;
    private String title;
    private Long stock;

    public BookStockDto() {

    }
    public BookStockDto(Long bookId, String title, Long stock) {
        this.bookId = bookId;
        this.title = title;
        this.stock = stock;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
