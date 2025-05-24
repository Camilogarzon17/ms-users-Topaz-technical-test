package com.topaz.ms_users.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.topaz.ms_users.infrastructure.constants.HttpConstants;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${spring.web.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(@org.springframework.lang.NonNull CorsRegistry registry) {
        registry.addMapping(HttpConstants.VAL_METHOD_MAPP)
                .allowedOriginPatterns(allowedOrigins)
                .allowedMethods(HttpConstants.VAL_METHOD_GET, HttpConstants.VAL_METHOD_POST,
                        HttpConstants.VAL_METHOD_PUT, HttpConstants.VAL_METHOD_DELETE, HttpConstants.VAL_METHOD_OPTIONS)
                .allowedHeaders(HttpConstants.VAL_METHOD_ALLOWED)
                .allowCredentials(true);
    }
}