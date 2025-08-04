// src/main/java/org/example/repository/CategoryRepository.java
package org.example.repository;

import org.example.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
