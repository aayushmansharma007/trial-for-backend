package com.example.practicekroonga.thikhai.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.practicekroonga.thikhai.service.ImageStorageService;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = {
    "https://trial-aayushmansharma007s-projects.vercel.app",

}, allowCredentials = "true")
public class ImageController {

    @Autowired
    private ImageStorageService imageStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            String imageUrl = imageStorageService.storeImage(file);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                .body("Failed to upload image: " + e.getMessage());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Image service is running");
    }
}