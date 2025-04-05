package com.example.practicekroonga.thikhai.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public class ImageStorageService {

    private final String UPLOAD_DIR = "uploads/";

    public String storeImage(MultipartFile file) throws java.io.IOException {
        try {
            // Create the directory if it doesn't exist
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // Get the file extension
            String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID().toString() + "." + fileExtension;

            // Define the target location
            Path targetLocation = Paths.get(UPLOAD_DIR + fileName);

            // Save the file
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Return the image URL (accessible path)
            return "/uploads/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }
}

