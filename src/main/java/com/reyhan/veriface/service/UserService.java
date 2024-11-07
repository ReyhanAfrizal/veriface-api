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

import com.reyhan.veriface.dto.UserDTO;
import com.reyhan.veriface.model.User;
import com.reyhan.veriface.repo.UserRepository;
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
        user.setPassword(userDTO.getPassword());
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
            user.setPassword(userDTO.getPassword());
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
}



