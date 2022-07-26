package com.example.customer.rest.configuration.handler;

import com.example.customer.rest.configuration.constant.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor responsible for authentication.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeaders(Headers.Authorization).nextElement();

        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        attributes.setAttribute(Headers.Authorization, token, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.setRequestAttributes(attributes);

        logger.info("Token: {}", token);

        if (token.isBlank()) {
            response.getWriter().println("Unauthorized token");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        } else {
            return true;
        }
    }
}
