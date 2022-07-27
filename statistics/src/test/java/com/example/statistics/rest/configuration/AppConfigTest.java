package com.example.statistics.rest.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link AppConfig}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 26.7.2022
 */
class AppConfigTest {

    private AppConfig appConfig;

    @Test
    void testAddInterceptors() {

        InterceptorRegistry interceptorRegistry = mock(InterceptorRegistry.class);
        when(interceptorRegistry.addInterceptor(any())).thenReturn(mock(InterceptorRegistration.class));

        appConfig = new AppConfig();
        appConfig.addInterceptors(interceptorRegistry);
        verify(interceptorRegistry, atLeastOnce()).addInterceptor(any());
    }
}
