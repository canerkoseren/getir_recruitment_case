package com.example.book.rest.configuration.handler;

import com.example.book.service.model.exception.BookProcessException;
import com.example.book.service.model.exception.BookValidationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Configuration
@ControllerAdvice
public class BookControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BookProcessException.class, BookValidationException.class})
    public ResponseEntity<String> handleOtherException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
