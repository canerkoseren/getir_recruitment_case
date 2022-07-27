package com.example.customer.rest.configuration.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit test implementation for {@link AuthenticationInterceptor}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 27.7.2022
 */
class AuthenticationInterceptorTest {

    AuthenticationInterceptor interceptor = new AuthenticationInterceptor();

    @Test
    void when_TokenIsNotEmpty_ReturnTrue() throws Exception {

        Enumeration<String> headers = new Enumeration<String>() {
            @Override
            public boolean hasMoreElements() {
                return true;
            }

            @Override
            public String nextElement() {
                return "token";
            }
        };

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeaders(anyString())).thenReturn(headers);

        HttpServletResponse response = mock(HttpServletResponse.class);

        boolean result = interceptor.preHandle(request, response, any());
        Assertions.assertTrue(result);
    }

    @Test
    void when_TokenIsEmpty_ReturnFalse() throws IOException {

        Enumeration<String> headers = new Enumeration<String>() {
            @Override
            public boolean hasMoreElements() {
                return false;
            }

            @Override
            public String nextElement() {
                return null;
            }
        };

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeaders(anyString())).thenReturn(headers);

        PrintWriter printWriter = mock(PrintWriter.class);
        doNothing().when(printWriter).println(anyString());

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(printWriter);
        doNothing().when(response).setStatus(anyInt());

        boolean result = interceptor.preHandle(request, response, any());
        Assertions.assertFalse(result);
    }
}
