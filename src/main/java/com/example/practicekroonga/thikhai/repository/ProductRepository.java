package com.example.practicekroonga.thikhai.repository;

import com.example.practicekroonga.thikhai.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Product.Category category);
}