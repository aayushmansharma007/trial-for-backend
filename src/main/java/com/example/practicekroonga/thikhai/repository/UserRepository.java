package com.example.practicekroonga.thikhai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.practicekroonga.thikhai.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}