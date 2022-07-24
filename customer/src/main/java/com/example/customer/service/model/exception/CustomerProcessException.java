package com.example.customer.service.model.exception;

/**
 * Exception class for customer processes.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public class CustomerProcessException extends Exception {

    public CustomerProcessException() {
    }

    public CustomerProcessException(String message) {
        super(message);
    }

    public CustomerProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
