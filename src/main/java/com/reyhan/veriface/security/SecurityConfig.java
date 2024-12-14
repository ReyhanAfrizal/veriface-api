package com.reyhan.veriface.security;


/*
IntelliJ IDEA 2024.2.3 (Community Edition)
Build #IC-242.23339.11, built on September 25, 2024
@Author Reyhan a.k.a. Reyhan Afrizal
Java Developer
Created on 11/25/2024 4:34 PM
@Last Modified 11/25/2024 4:34 PM
Version 1.0
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.mindrot.jbcrypt.BCrypt;

@Configuration
public class SecurityConfig {

    // You don't need a PasswordEncoder bean if you're manually using BCrypt
    @Bean
    public BCrypt bCrypt() {
        return new BCrypt(); // You would use this to manually hash and verify passwords
    }

    // Example of hashing a password
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Example of verifying a password
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}

