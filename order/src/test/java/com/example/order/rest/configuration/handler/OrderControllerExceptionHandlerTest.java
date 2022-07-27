package com.example.order.rest.configuration.handler;

import com.example.order.service.model.exception.OrderProcessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test implementation for {@link OrderControllerExceptionHandler}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 26.7.2022
 */
class OrderControllerExceptionHandlerTest {

    @Test
    void testHandler() {

        OrderProcessException exception = mock(OrderProcessException.class);
        when(exception.getMessage()).thenReturn("exception-message");

        OrderControllerExceptionHandler handler = new OrderControllerExceptionHandler();
        ResponseEntity<String> entity = handler.handleOtherException(exception, mock(WebRequest.class));
        Assertions.assertNotNull(entity);
    }
}
