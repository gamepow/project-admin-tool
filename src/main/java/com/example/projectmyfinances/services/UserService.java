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
    
    /**
     * Changes the password of a user.
     * @param userId The ID of the user.
     * @param currentPassword The current password of the user.
     * @param newPassword The new password to set.
     * @throws Exception If the current password is incorrect or any other error occurs.
     */
    void changePassword(int userId, String currentPassword, String newPassword) throws Exception;
}
