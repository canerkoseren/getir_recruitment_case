package com.example.customer.service.model.exception;

/**
 * Exception class for customer validation.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public class CustomerValidationException extends Exception {

    public CustomerValidationException() {
    }

    public CustomerValidationException(String message) {
        super(message);
    }

    public CustomerValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
