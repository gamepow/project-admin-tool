package com.example.projectmyfinances.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmyfinances.entities.User;
import com.example.projectmyfinances.entities.UserProfile;
import com.example.projectmyfinances.repositories.UserProfileRepository;
import com.example.projectmyfinances.repositories.UserRepository;

@Service
public class UserService {
 
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserProfile> findUserProfileByUser(User user){
        return userProfileRepository.findByUserId(user.getUserId());
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }
}
