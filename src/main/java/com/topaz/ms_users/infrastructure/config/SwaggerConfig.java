package com.topaz.ms_users.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.properties.project-name}")
    private String projectName;

    @Value("${swagger.properties.version}")
    private String version;

    @Value("${swagger.properties.project-short-description}")
    private String description;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(projectName)
                        .version(version)
                        .description(description));
    }
}
