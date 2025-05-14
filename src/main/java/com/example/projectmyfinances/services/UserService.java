package com.example.projectmyfinances.services;

import java.util.Optional;

import com.example.projectmyfinances.dto.UserDTO;
import com.example.projectmyfinances.dto.UserProfileDTO;
import com.example.projectmyfinances.entities.User;

public interface UserService {
 
    public Optional<User> findByUsername(String username);

    public UserProfileDTO findUserProfileByUser(User user);

    public Optional<User> findById(Integer id);

    public User saveUser(UserDTO userDTO) throws Exception;

    public void updateUserProfile(UserProfileDTO userProfileDTO, int id) throws Exception;
}
