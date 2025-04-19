package com.example.projectmyfinances.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService; // Your service to fetch user data from the database

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the database
        com.example.projectmyfinances.entities.User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Return a Spring Security User object
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Ensure this is encoded (e.g., BCrypt)
                .roles(new String[0]) // Convert roles to String array
                .build();
    }
}
