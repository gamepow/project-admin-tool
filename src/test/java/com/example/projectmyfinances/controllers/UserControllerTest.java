package com.example.projectmyfinances.controllers;

import com.example.projectmyfinances.entities.User;
import com.example.projectmyfinances.entities.UserProfile;
import com.example.projectmyfinances.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // Add these inside your test class
    @MockBean
    private com.example.projectmyfinances.security.JwtUtil jwtUtil;

    @MockBean
    private com.example.projectmyfinances.security.JwtRequestFilter jwtRequestFilter;

    @Test
    void getUserByUserName_found() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setUsername("test");
        Mockito.when(userService.findByUsername("test")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/user/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test"));
    }

    @Test
    void getUserByUserName_notFound() throws Exception {
        Mockito.when(userService.findByUsername("jane")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/user/jane"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserProfileByUser_found() throws Exception {
        UserProfile profile = new UserProfile();
        profile.setId(1);
        Mockito.when(userService.findUserProfileByUser(Mockito.any(User.class)))
                .thenReturn(Optional.of(profile));

        mockMvc.perform(get("/api/user/1/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getUserProfileByUser_notFound() throws Exception {
        Mockito.when(userService.findUserProfileByUser(Mockito.any(User.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/user/2/profile"))
                .andExpect(status().isNotFound());
    }
}
