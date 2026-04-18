package com.example.InventorySearch.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration          // Tells Spring: this class has configuration beans
public class SwaggerConfig {

    @Bean               // Spring manages this object
    public OpenAPI inventoryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Inventory Search API")
                        .description("REST API for searching and filtering inventory items")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Your Name")
                                .email("your@email.com")));
    }
}
