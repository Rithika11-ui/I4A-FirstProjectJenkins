package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class PhotoService {

    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png");
    private static final long MAX_SIZE = 5 * 1024 * 1024; // 5 MB

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    /**
     * Validates and saves photo to local storage.
     * Returns the saved filename.
     */
    public String savePhoto(MultipartFile file) throws IOException {
        validatePhoto(file);

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String ext = getExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID() + "." + ext;
        Path dest = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }

    /**
     * Validates photo file type and size.
     */
    public void validatePhoto(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Photo file is empty");
        }
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("Only JPEG and PNG images are allowed");
        }
        if (file.getSize() > MAX_SIZE) {
            throw new IllegalArgumentException("Photo must be under 5 MB");
        }
    }

    /**
     * Deletes a photo file from local storage.
     */
    public void deletePhoto(String filename) {
        if (filename == null || filename.isBlank()) return;
        try {
            Path path = Paths.get(uploadDir).resolve(filename);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.err.println("Could not delete photo: " + filename);
        }
    }

    /**
     * Reads photo as byte array (for serving via HTTP).
     */
    public byte[] readPhoto(String filename) throws IOException {
        Path path = Paths.get(uploadDir).resolve(filename);
        return Files.readAllBytes(path);
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "jpg";
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }
}