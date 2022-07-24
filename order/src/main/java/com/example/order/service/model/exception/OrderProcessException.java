package com.example.order.service.model.exception;

/**
 * Exception class for order processes.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public class OrderProcessException extends Exception{

    public OrderProcessException() {
    }

    public OrderProcessException(String message) {
        super(message);
    }

    public OrderProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
