package org.example.controller;

import org.example.model.Category;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // ✅ Get All Categories
    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    // ✅ Create Category
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.save(category));
    }

    // ✅ Update Category
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.update(id, category));
    }

    // ✅ Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
