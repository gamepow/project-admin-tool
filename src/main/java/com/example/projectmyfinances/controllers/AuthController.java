package com.example.projectmyfinances.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectmyfinances.entities.CorsConfig;
import com.example.projectmyfinances.entities.User;
import com.example.projectmyfinances.security.JwtUtil;
import com.example.projectmyfinances.services.UserService;
import com.example.projectmyfinances.services.UserServiceImpl;

@RestController
@RequestMapping("/api/public/auth")
public class AuthController {

    private final SecurityFilterChain filterChain;

    private final CorsConfig corsConfig;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    AuthController(CorsConfig corsConfig, SecurityFilterChain filterChain) {
        this.corsConfig = corsConfig;
        this.filterChain = filterChain;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        Optional<User> userOptional = userService.findByUsername(loginRequest.getUsername());

        if(userOptional.isPresent()){

            User user = userOptional.get();

            //System.out.println("Stored Password: " + bCryptPasswordEncoder.encode(loginRequest.getPassword()));

            if (bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername());
                return ResponseEntity.ok(new LoginResponse(token, "User logged in", user.getUserId()));
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }        
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    
    static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {return username;}
        public String getPassword() {return password;}
    }

    static class LoginResponse {
        private String token;
        private String message;
        private Integer id;

        public LoginResponse(String token, String message, Integer userId) {
            this.token = token;
            this.message = message;
            this.id = userId;
        }

        public String getToken() {return token;}
        public String getMessage() {return message;}
        public Integer getId(){return id;}
    }
    
}
