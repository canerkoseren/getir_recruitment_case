package com.example.book.rest.configuration.handler;

import com.example.book.service.model.exception.BookProcessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test implementation for {@link BookControllerExceptionHandler}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 26.7.2022
 */
class BookControllerExceptionHandlerTest {

    @Test
    void testHandler(){

        BookProcessException exception = mock(BookProcessException.class);
        when(exception.getMessage()).thenReturn("exception-message");

        BookControllerExceptionHandler handler = new BookControllerExceptionHandler();
        ResponseEntity<String> entity = handler.handleOtherException(exception, mock(WebRequest.class));
        Assertions.assertNotNull(entity);
    }
}
