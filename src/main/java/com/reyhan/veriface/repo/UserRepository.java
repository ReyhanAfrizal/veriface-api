package com.reyhan.veriface.repo;


/*
IntelliJ IDEA 2024.2.3 (Community Edition)
Build #IC-242.23339.11, built on September 25, 2024
@Author Reyhan a.k.a. Reyhan Afrizal
Java Developer
Created on 11/5/2024 11:51 AM
@Last Modified 11/5/2024 11:51 AM
Version 1.0
*/

import com.reyhan.veriface.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByNip(String nip); // Find user by NIP
}
