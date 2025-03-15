package com.example.projectadmin.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectadmin.Services.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        
        // TODO: incluir validation de usuario y contraseña
        
        String token = jwtService.generateToken(loginRequest.getUsername());

        return ResponseEntity.ok(new LoginResponse(token, "User logged in"));
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

        public LoginResponse(String token, String message) {
            this.token = token;
            this.message = message;
        }

        public String getToken() {return token;}
        public String getMessage() {return message;}
    }
    
}
