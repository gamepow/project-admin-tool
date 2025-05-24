package com.example.projectmyfinances.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projectmyfinances.dto.UserDTO;
import com.example.projectmyfinances.dto.UserProfileDTO;
import com.example.projectmyfinances.entities.User;
import com.example.projectmyfinances.entities.UserProfile;
import com.example.projectmyfinances.repositories.UserProfileRepository;
import com.example.projectmyfinances.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    String DEFAULT_CURRENCY = "CRC";

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserProfileDTO findUserProfileByUser(User user){
        
        // 1. Retrieve user's profile from repository
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(user.getUserId());

        // 2. Check if user profile is present
        if (!userProfile.isPresent()) {
            return new UserProfileDTO();
        }
        else{
            // 3. Map UserProfile to UserProfileDTO
            UserProfileDTO userProfileDTO = new UserProfileDTO();
            userProfileDTO.setUsername(userProfile.get().getUser().getUsername());
            userProfileDTO.setFirstName(userProfile.get().getFirstName());
            userProfileDTO.setLastName(userProfile.get().getLastName());
            userProfileDTO.setDefaultCurrency(userProfile.get().getDefaultCurrency());

            return userProfileDTO;
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
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
        userProfile.setDefaultCurrency(DEFAULT_CURRENCY);
        userProfileRepository.save(userProfile);

        return newUser;
    }

    @Override
    public void updateUserProfile(UserProfileDTO userProfileDTO, int id) throws Exception {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByUserId(id);

        if (!optionalUserProfile.isPresent()) {
            throw new Exception("User profile not found.");
        }

        UserProfile userProfile = optionalUserProfile.get();
        userProfile.setFirstName(userProfileDTO.getFirstName());
        userProfile.setLastName(userProfileDTO.getLastName());
        userProfile.setDefaultCurrency(userProfileDTO.getDefaultCurrency());

        userProfileRepository.save(userProfile);
    }

    @Override
    public void changePassword(int userId, String currentPassword, String newPassword) throws Exception {
        // Fetch the user by ID
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new Exception("User not found.");
        }

        User user = optionalUser.get();

        // Validate the current password
        if (!bCryptPasswordEncoder.matches(currentPassword, user.getPassword())) {
            throw new Exception("Current password is incorrect.");
        }

        // Hash the new password
        String hashedNewPassword = bCryptPasswordEncoder.encode(newPassword);

        // Update the user's password
        user.setPassword(hashedNewPassword);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        User user = userOptional.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities("USER") // You can customize roles/authorities here
                .build();
    }
}
