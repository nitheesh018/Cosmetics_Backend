package org.example.service;

import org.example.model.Category;
import org.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // ✅ Get All Categories
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    // ✅ Get Category by ID
    public Optional<Category> getById(Long id) {
        return categoryRepository.findById(id);
    }

    // ✅ Create Category
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    // ✅ Update Category
    public Category update(Long id, Category updated) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existing.setName(updated.getName());
        return categoryRepository.save(existing);
    }

    // ✅ Delete Category
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
