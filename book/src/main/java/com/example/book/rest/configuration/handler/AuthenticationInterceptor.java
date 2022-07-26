package com.example.book.rest.configuration.handler;

import com.example.book.rest.configuration.constant.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String token = request.getHeaders(Headers.AUTHORIZATION).nextElement();

        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

        if (Objects.nonNull(attributes)) {
            attributes.setAttribute(Headers.AUTHORIZATION, token, RequestAttributes.SCOPE_REQUEST);
            RequestContextHolder.setRequestAttributes(attributes);
        }

        if (Objects.isNull(token) || token.isEmpty()) {
            response.getWriter().println("Unauthorized token");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        } else {

            logger.info("Token: {}", token);
            return true;
        }
    }
}
