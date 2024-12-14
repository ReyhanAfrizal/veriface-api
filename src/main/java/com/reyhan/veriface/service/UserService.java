package com.reyhan.veriface.service;

/*
IntelliJ IDEA 2024.2.3 (Community Edition)
Build #IC-242.23339.11, built on September 25, 2024
@Author Reyhan a.k.a. Reyhan Afrizal
Java Developer
Created on 11/5/2024 11:55 AM
@Last Modified 11/5/2024 11:55 AM
Version 1.0
*/


import com.reyhan.veriface.dto.LoginDTO;
import com.reyhan.veriface.dto.UserDTO;
import com.reyhan.veriface.model.User;
import com.reyhan.veriface.repo.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setNamaUser(userDTO.getNamaUser());
        user.setNip(userDTO.getNip());
        user.setEmail(userDTO.getEmail());
        // Hash the password before saving
        String hashedPassword = hashPassword(userDTO.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    // Get a user by ID
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update a user
    public Optional<User> updateUser(Integer id, UserDTO userDTO) {
        return userRepository.findById(id).map(user -> {
            user.setNamaUser(userDTO.getNamaUser());
            user.setNip(userDTO.getNip());
            user.setEmail(userDTO.getEmail());
            // Hash the password before updating
            String hashedPassword = hashPassword(userDTO.getPassword());
            user.setPassword(hashedPassword);
            return userRepository.save(user);
        });
    }

    // Delete a user by ID
    public boolean deleteUserById(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // Login method: compare nip and password
    public User login(LoginDTO loginDTO) {
        // Find user by nip
        Optional<User> userOptional = userRepository.findByNip(loginDTO.getNip());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Verify the password using BCrypt
            if (verifyPassword(loginDTO.getPassword(), user.getPassword())) {
                return user; // Password matches, return the user
            }
        }

        return null; // Invalid credentials
    }

    // Method to hash the password
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Method to verify the password
    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
