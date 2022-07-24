package com.example.book.service.model.exception;

/**
 * Exception class for book processes.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public class BookProcessException extends Exception {

    public BookProcessException() {
    }

    public BookProcessException(String message) {
        super(message);
    }

    public BookProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
