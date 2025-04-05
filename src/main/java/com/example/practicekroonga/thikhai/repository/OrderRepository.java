package com.example.practicekroonga.thikhai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.practicekroonga.thikhai.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}