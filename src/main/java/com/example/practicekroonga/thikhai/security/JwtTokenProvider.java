package com.example.practicekroonga.thikhai.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.example.practicekroonga.thikhai.Entity.User;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Long userId, User.Role role) {
        return Jwts.builder()
            .claim("userId", userId)
            .claim("role", role)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }
}