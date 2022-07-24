package com.example.book.service.model.exception;

/**
 * Exception class for book validation.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public class BookValidationException extends Exception {

    public BookValidationException() {
    }

    public BookValidationException(String message) {
        super(message);
    }

    public BookValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
