package com.example.atv2arqsoftware.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Produtos")
                        .description("API para gerenciamento de produtos")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Roberto Luis")
                                .email("seu.email@exemplo.com")));
    }
}