package com.example.projectmyfinances.services;

import java.util.Optional;

import com.example.projectmyfinances.dto.UserDTO;
import com.example.projectmyfinances.entities.User;
import com.example.projectmyfinances.entities.UserProfile;

public interface UserService {
 
    public Optional<User> findByUsername(String username);

    public Optional<UserProfile> findUserProfileByUser(User user);

    public Optional<User> findById(Integer id);

    public User saveUser(UserDTO userDTO) throws Exception;
}
