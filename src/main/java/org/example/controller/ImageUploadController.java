package org.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageUploadController {

    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = "http://localhost:8080/uploads/" + fileName;
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + e.getMessage());
        }
    }
}
