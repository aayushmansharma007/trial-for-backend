package com.example.practicekroonga.thikhai.service;


import com.example.practicekroonga.thikhai.Entity.CartItem;
import com.example.practicekroonga.thikhai.Entity.Order;
import com.example.practicekroonga.thikhai.Entity.Product;
import com.example.practicekroonga.thikhai.repository.OrderRepository;
import com.example.practicekroonga.thikhai.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    public Order placeOrder(Order order) {
        // Create new CartItem instances to avoid detached entity issues
        List<CartItem> newItems = new ArrayList<>();
        for (CartItem item : order.getItems()) {
            CartItem newItem = new CartItem();
            // Fetch the Product to ensure it's managed
            Product managedProduct = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            newItem.setProduct(managedProduct);
            newItem.setQuantity(item.getQuantity());
            newItems.add(newItem);
        }
        order.setItems(newItems);

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(order.getUserId()); // Clear cart after order
        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}