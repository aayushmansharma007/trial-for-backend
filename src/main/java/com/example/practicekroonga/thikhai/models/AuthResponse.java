package com.example.practicekroonga.thikhai.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthResponse extends ApiResponse {
    private String token;
    private Long userId;
    private String username;

    public AuthResponse(boolean success, String message, String token, Long userId, String username) {
        super(success, message);
        this.token = token;
        this.userId = userId;
        this.username = username;
    }
}