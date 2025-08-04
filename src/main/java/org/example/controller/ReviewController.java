package org.example.controller;

import org.example.model.Review;
import org.example.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{productId}")
    public ResponseEntity<Review> addReview(
            @PathVariable Long productId,
            @RequestBody Map<String, Object> payload) {

        int rating = Integer.parseInt(payload.get("rating").toString());
        String comment = (String) payload.get("comment");
        String email = (String) payload.get("email");

        Review review = reviewService.addReview(productId, email, rating, comment);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<Review>> getReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsForProduct(productId));
    }
}
