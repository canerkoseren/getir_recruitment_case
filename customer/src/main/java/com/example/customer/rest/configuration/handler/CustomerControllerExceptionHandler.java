package com.example.customer.rest.configuration.handler;

import com.example.customer.service.model.exception.CustomerProcessException;
import com.example.customer.service.model.exception.CustomerValidationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Customized response entity exception handler.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@Configuration
@ControllerAdvice
public class CustomerControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomerValidationException.class, CustomerProcessException.class})
    public ResponseEntity<String> handleOtherException(Exception ex, WebRequest request) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
