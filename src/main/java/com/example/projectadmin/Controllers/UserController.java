package com.example.projectadmin.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectadmin.entities.User;
import com.example.projectadmin.entities.UserProfile;
import com.example.projectadmin.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String username) {
        
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<UserProfile> getUserProfileByUser(@PathVariable int id){
        Optional<UserProfile> userProfile = userService.findUserProfileByUser(new User(id));
        if(userProfile.isPresent()){
            return ResponseEntity.ok(userProfile.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
