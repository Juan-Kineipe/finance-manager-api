package com.kineipe.financemanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Finance Manager API made with Java 17 and Spring Boot 3")
                        .version("v1")
                        .description("")
                        .license(new License()
                                .name("MIT")
                                .url("https://www.mit.edu/~amini/LICENSE.md"))
                );
    }

}
