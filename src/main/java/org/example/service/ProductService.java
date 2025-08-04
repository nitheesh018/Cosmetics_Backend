package org.example.service;

import org.example.model.Category;
import org.example.model.Product;
import org.example.repository.CategoryRepository;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String UPLOAD_DIR = "uploads/";

    // ✅ Get all products
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    // ✅ Get product by ID
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    // ✅ Search products by keyword
    public List<Product> search(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    // ✅ Save product (create)
    public Product save(String name, String description, double price, MultipartFile imageFile, String imageUrl, Long categoryId) {
        try {
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);

            // Image file or URL
            String savedImageUrl = handleImage(imageFile, imageUrl);
            product.setImageUrl(savedImageUrl);

            // Set category
            if (categoryId != null) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
                product.setCategory(category);
            }

            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Failed to save product: " + e.getMessage());
        }
    }

    // ✅ Update existing product
    public Product update(Long id, String name, String description, double price, MultipartFile imageFile, String imageUrl, Long categoryId) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);

            // Replace image if new provided
            String updatedImageUrl = handleImage(imageFile, imageUrl);
            if (updatedImageUrl != null) {
                product.setImageUrl(updatedImageUrl);
            }

            // Update category
            if (categoryId != null) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
                product.setCategory(category);
            }

            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Failed to update product: " + e.getMessage());
        }
    }

    // ✅ Delete product
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    // ✅ Handle image upload or URL fallback
    private String handleImage(MultipartFile imageFile, String imageUrl) {
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                return "/images/" + fileName;
            } catch (IOException e) {
                throw new RuntimeException("❌ Failed to save image file: " + e.getMessage());
            }
        } else if (imageUrl != null && !imageUrl.isEmpty()) {
            return imageUrl;
        } else {
            throw new RuntimeException("⚠️ Image is required — either upload a file or provide a URL.");
        }
    }
}
