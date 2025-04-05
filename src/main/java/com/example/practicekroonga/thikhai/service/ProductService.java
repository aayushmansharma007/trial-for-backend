package com.example.practicekroonga.thikhai.service;

import com.example.practicekroonga.thikhai.Entity.Product;
import com.example.practicekroonga.thikhai.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    private static final String UPLOAD_DIR = "uploads/";

    public Product saveProduct(Product product, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                Files.createDirectories(Paths.get(UPLOAD_DIR));
                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                Files.write(filePath, image.getBytes());
                product.setImageUrl("http://localhost:8080/" + UPLOAD_DIR + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to store image", e);
            }
        }
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}