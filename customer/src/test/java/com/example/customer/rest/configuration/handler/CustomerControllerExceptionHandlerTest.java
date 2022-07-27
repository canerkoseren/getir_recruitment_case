package com.example.customer.rest.configuration.handler;

import com.example.customer.service.model.exception.CustomerProcessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test implementation for {@link CustomerControllerExceptionHandler}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 26.7.2022
 */
class CustomerControllerExceptionHandlerTest {

    @Test
    void testHandler() {

        CustomerProcessException exception = mock(CustomerProcessException.class);
        when(exception.getMessage()).thenReturn("exception-message");

        CustomerControllerExceptionHandler handler = new CustomerControllerExceptionHandler();
        ResponseEntity<String> entity = handler.handleOtherException(exception, mock(WebRequest.class));
        Assertions.assertNotNull(entity);
    }
}
