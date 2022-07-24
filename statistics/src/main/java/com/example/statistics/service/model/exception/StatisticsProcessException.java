package com.example.statistics.service.model.exception;

/**
 * Exception class for order processes.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public class StatisticsProcessException extends Exception{

    public StatisticsProcessException() {
    }

    public StatisticsProcessException(String message) {
        super(message);
    }

    public StatisticsProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
