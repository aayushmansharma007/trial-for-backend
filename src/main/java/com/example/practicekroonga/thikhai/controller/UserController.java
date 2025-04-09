package com.example.practicekroonga.thikhai.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.practicekroonga.thikhai.Entity.User;
import com.example.practicekroonga.thikhai.models.UserLoginRequest;
import com.example.practicekroonga.thikhai.models.UserLoginResponse;
import com.example.practicekroonga.thikhai.models.UserRegistrationRequest;
import com.example.practicekroonga.thikhai.models.UserRegistrationResponse;
import com.example.practicekroonga.thikhai.models.UserResponse;
import com.example.practicekroonga.thikhai.security.JwtTokenProvider;
import com.example.practicekroonga.thikhai.service.UserService;

import java.util.Optional;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Place more specific routes first
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        try {
            Optional<User> userOpt = userService.loginUser(request.getUsername(), request.getPassword());
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                String token = jwtTokenProvider.generateToken(user.getId(), user.getRole());
                
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("token", token);
                response.put("userId", user.getId());
                response.put("username", user.getUsername());
                
                return ResponseEntity.ok(response);
            }
            
            return ResponseEntity.status(401)
                .body(Collections.singletonMap("message", "Invalid credentials"));
        } catch (Exception e) {
            return ResponseEntity.status(401)
                .body(Collections.singletonMap("message", "Login failed: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        try {
            // Validation checks
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "Username is required"));
            }

            // Check if username already exists
            if (userService.findByUsername(request.getUsername()).isPresent()) {
                return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "Username already exists"));
            }

            // Check if email already exists
            if (userService.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "Email already exists"));
            }

            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setPhone(request.getPhone());
            user.setRole(User.Role.CUSTOMER);

            User savedUser = userService.registerUser(user);

            // Create response with token
            String token = jwtTokenProvider.generateToken(savedUser.getId(), savedUser.getRole());
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Registration successful");
            response.put("token", token);
            response.put("userId", savedUser.getId());
            response.put("username", savedUser.getUsername());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("message", "Registration failed: " + e.getMessage()));
        }
    }

    // Place more generic routes last
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            UserResponse response = new UserResponse();
            response.setId(user.get().getId());
            response.setFirstName(user.get().getFirstName());
            response.setUsername(user.get().getUsername());
            response.setEmail(user.get().getEmail());
            response.setPhone(user.get().getPhone());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
}
