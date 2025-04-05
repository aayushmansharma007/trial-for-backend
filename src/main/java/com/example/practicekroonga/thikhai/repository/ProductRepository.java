// ProductRepository.java
package com.example.practicekroonga.thikhai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.practicekroonga.thikhai.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}