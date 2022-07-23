package com.example.book.rest.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @PostConstruct
    public void init(){
        logger.info("AppConfig has been configured.");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor());
        logger.info("RestInterceptor has been registered");
    }

    /*
        @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/account/login");
    }
     */
}
