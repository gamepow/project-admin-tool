package com.example.projectmyfinances.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmyfinances.dto.UserDTO;
import com.example.projectmyfinances.entities.User;
import com.example.projectmyfinances.entities.UserProfile;
import com.example.projectmyfinances.repositories.UserProfileRepository;
import com.example.projectmyfinances.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

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

    public User saveUser(UserDTO userDTO) throws Exception{

        Optional<User> optionalUser = userRepository.findByUsername(userDTO.getUsername());

        if (optionalUser.isPresent()) {
            throw new Exception("User already exists.");
        }

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword());

        newUser = userRepository.save(newUser);

        // Create default user profile
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(newUser);
        userProfile.setFirstName(userDTO.getFirstName());
        userProfile.setLastName(userDTO.getLastName());
        userProfile.setDefaultCurrency("CRC");
        userProfileRepository.save(userProfile);

        return newUser;
    }

}
