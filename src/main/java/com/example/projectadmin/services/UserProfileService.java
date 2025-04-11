package com.example.projectadmin.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectadmin.entities.UserProfile;
import com.example.projectadmin.repositories.UserProfileRepository;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public Optional<UserProfile> findByUserId(Integer userId) {
        return userProfileRepository.findByUserId(userId);
    }

}
