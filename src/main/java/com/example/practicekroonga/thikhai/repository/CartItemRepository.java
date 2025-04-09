package com.example.practicekroonga.thikhai.repository;

import com.example.practicekroonga.thikhai.Entity.CartItem;
import com.example.practicekroonga.thikhai.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
}