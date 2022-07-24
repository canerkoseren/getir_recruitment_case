package com.example.statistics.service.model.exception;

/**
 * Exception class for order validation.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public class StatisticsValidationException extends Exception{

    public StatisticsValidationException() {
    }

    public StatisticsValidationException(String message) {
        super(message);
    }

    public StatisticsValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
