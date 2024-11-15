package com.reyhan.veriface.config;


/*
IntelliJ IDEA 2024.2.3 (Community Edition)
Build #IC-242.23339.11, built on September 25, 2024
@Author Reyhan a.k.a. Reyhan Afrizal
Java Developer
Created on 11/13/2024 9:29 AM
@Last Modified 11/13/2024 9:29 AM
Version 1.0
*/

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Veriface Api Implementation")
                        .description("API documentation for Veriface App")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Reyhan Afrizal")
                                .email("reyhanafrizal@gmail.com")
                                .url("https://yourwebsite.com")));
    }
}
