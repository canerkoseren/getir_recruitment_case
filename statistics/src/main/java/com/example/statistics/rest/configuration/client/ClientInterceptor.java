package com.example.statistics.rest.configuration.client;

import com.example.statistics.rest.configuration.constant.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;

/**
 * Client interceptor to add header into existent headers.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public class ClientInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ClientInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

        if (attributes != null) {

            String token = (String) attributes.getAttribute(Headers.AUTHORIZATION, RequestAttributes.SCOPE_REQUEST);

            HttpHeaders headers = request.getHeaders();
            headers.add(Headers.AUTHORIZATION, token);

            logger.info("Token: {}", token);
        }

        return execution.execute(request, body);
    }
}
