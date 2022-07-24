package com.example.order.service.model.exception;

/**
 * Exception class for order validation.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public class OrderValidationException extends Exception{

    public OrderValidationException() {
    }

    public OrderValidationException(String message) {
        super(message);
    }

    public OrderValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
