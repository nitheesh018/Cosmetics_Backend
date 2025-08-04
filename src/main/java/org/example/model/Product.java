package org.example.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    private double price;

    @Column(length = 10000)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("products")
    private Category category;

    // ✅ New: Link product to many reviews
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("product") // prevent back-reference from review -> product -> review...
    private List<Review> reviews;

    // === Getters and Setters ===

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
}
