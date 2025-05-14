package com.example.projectmyfinances.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectmyfinances.dto.UserDTO;
import com.example.projectmyfinances.dto.UserProfileDTO;
import com.example.projectmyfinances.entities.User;
import com.example.projectmyfinances.services.UserServiceImpl;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/private/{username}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String username) {
        
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/private/profile/{id}")
    public ResponseEntity<?> getUserProfileByUser(@PathVariable int id){
        try {
            UserProfileDTO userProfile = userService.findUserProfileByUser(new User(id));

            // 2. return the list of transactions as a JSON response
            if(userProfile.getUsername() != null){
                logger.info("User profile found: {}", userProfile);
                return ResponseEntity.ok(userProfile);
            }else{
                return ResponseEntity.notFound().build();
            }    

        } catch (Exception e) {
            logger.error("Error retrieving user profile: {}", e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "User profile not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/private/profile/{id}")
    public ResponseEntity<?> updateUserProfule(@RequestBody UserProfileDTO userProfileDTO, @PathVariable int id) {
        try{
            userService.updateUserProfile(userProfileDTO, id);

            return ResponseEntity.ok("User profile updated successfully"); // <-- return JSON

        }catch(Exception ex){
            logger.error("Error updating user profile: {}", ex.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "User profile not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    

    @PostMapping("/public/newuser")
    public ResponseEntity<?> saveNewuser(@RequestBody UserDTO userDTO) {
        try {

            logger.info("Received request to create new user: {}", userDTO.getUsername());

            // Encrypt the password
            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

            // Create a new User object
            User newUser = userService.saveUser(userDTO);

            logger.info("User created successfully: {}", newUser.getUserId());
            
            // Return a JSON object with the transaction ID and a message
            Map<String, Object> response = new HashMap<>();
            response.put("userId", newUser.getUserId());
            response.put("message", "User created successfully");

            return ResponseEntity.ok(response);
            
        } catch (Exception ex) {
            logger.error("Error creating user: {}", ex.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "User already exists.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    
    
}
