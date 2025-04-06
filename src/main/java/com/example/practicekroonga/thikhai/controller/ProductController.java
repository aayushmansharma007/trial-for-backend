package com.example.practicekroonga.thikhai.controller;

import com.example.practicekroonga.thikhai.Entity.Product;
import com.example.practicekroonga.thikhai.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "https://trial-aayushmansharma007s-projects.vercel.app")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam("stock") boolean stock,
            @RequestParam("category") Product.Category category, // New category param
            @RequestParam(value = "image", required = false) MultipartFile image) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setStock(stock);
        product.setCategory(category);
        Product savedProduct = productService.saveProduct(product, image);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam("stock") boolean stock,
            @RequestParam("category") Product.Category category, // New category param
            @RequestParam(value = "image", required = false) MultipartFile image) {
        Product product = productService.getProductById(id);
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setStock(stock);
        product.setCategory(category);
        Product updatedProduct = productService.saveProduct(product, image);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}