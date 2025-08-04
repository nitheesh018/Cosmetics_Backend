package org.example.controller;

import org.example.model.Product;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ Get All Products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    // ✅ Get Product By ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Search Products by Name
    @GetMapping("/search/{keyword}")
    public List<Product> searchProducts(@PathVariable String keyword) {
        return productService.search(keyword);
    }

    // ✅ Create New Product
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Product> createProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "imageUrl", required = false) String imageUrl,
            @RequestParam("categoryId") Long categoryId
    ) {
        Product created = productService.save(name, description, price, imageFile, imageUrl, categoryId);
        return ResponseEntity.ok(created);
    }

    // ✅ Update Existing Product
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "imageUrl", required = false) String imageUrl,
            @RequestParam("categoryId") Long categoryId
    ) {
        Product updated = productService.update(id, name, description, price, imageFile, imageUrl, categoryId);
        return ResponseEntity.ok(updated);
    }

    // ✅ Delete Product
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
