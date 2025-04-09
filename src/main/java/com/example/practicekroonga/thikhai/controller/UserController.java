package com.example.practicekroonga.thikhai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.practicekroonga.thikhai.Entity.User;
import com.example.practicekroonga.thikhai.models.ApiResponse;
import com.example.practicekroonga.thikhai.models.AuthResponse;
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
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
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
                
                return ResponseEntity.ok(new AuthResponse(
                    true,
                    "Login successful",
                    token,
                    user.getId(),
                    user.getUsername()
                ));
            }
            
            return ResponseEntity.status(401)
                .body(new ApiResponse(false, "Invalid credentials"));
        } catch (Exception e) {
            return ResponseEntity.status(401)
                .body(new ApiResponse(false, "Login failed: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        try {
            // Validate request
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Username is required"));
            }

            if (userService.findByUsername(request.getUsername()).isPresent()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Username already exists"));
            }

            if (userService.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Email already exists"));
            }

            // Create and save user
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setFirstName(request.getFirstName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());

            User savedUser = userService.registerUser(user);
            String token = jwtTokenProvider.generateToken(savedUser.getId(), savedUser.getRole());

            return ResponseEntity.ok(new AuthResponse(
                true,
                "Registration successful",
                token,
                savedUser.getId(),
                savedUser.getUsername()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, "Registration failed: " + e.getMessage()));
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
