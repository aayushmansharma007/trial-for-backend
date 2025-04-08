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
        Optional<User> user = userService.loginUser(request.getUsername(), request.getPassword());
        if (user.isPresent()) {
            String token = jwtTokenProvider.generateToken(user.get().getId(), user.get().getRole());
            UserLoginResponse response = new UserLoginResponse();
            response.setToken(token);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(User.Role.CUSTOMER);

        User savedUser = userService.registerUser(user);

        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setPhone(savedUser.getPhone());
        response.setUsername(savedUser.getUsername());

        return ResponseEntity.ok(response);
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
