// CartRepository.java
package com.example.practicekroonga.thikhai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.practicekroonga.thikhai.Entity.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
}