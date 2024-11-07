package com.reyhan.veriface.config;


/*
IntelliJ IDEA 2024.2.3 (Community Edition)
Build #IC-242.23339.11, built on September 25, 2024
@Author Reyhan a.k.a. Reyhan Afrizal
Java Developer
Created on 11/6/2024 4:24 PM
@Last Modified 11/6/2024 4:24 PM
Version 1.0
*/

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dd9ljx3y6",
                "api_key", "767962387171534",
                "api_secret", "Z6ABcq8QHokcIGyOBkj-d2xnmKs"));
    }
}
