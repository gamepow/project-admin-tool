package com.example.projectadmin.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectadmin.entities.User;
import com.example.projectadmin.repositories.UserRepository;

@Service
public class UserService {
 
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
