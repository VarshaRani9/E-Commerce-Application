package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Review;
import com.example.demo.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable Long productId) {
        logger.info("Received request to fetch reviews for productId: {}", productId);
        try {
            List<Review> reviews = reviewService.getReviewsByProductId(productId);
            if (reviews.isEmpty()) {
                logger.warn("No reviews found for productId: {}", productId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if no reviews found
            }
            logger.info("Successfully fetched {} reviews for productId: {}", reviews.size(), productId);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching reviews for productId: {}. Error: {}", productId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Return 500 on unexpected errors
        }
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        logger.info("Received request to add review for productId: {} by userId: {}", review.getProduct().getId(), review.getUser().getId());

        if (review.getRating() < 1 || review.getRating() > 5) {
            logger.warn("Invalid rating value: {}. Must be between 1 and 5.", review.getRating());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Invalid rating
        }

        try {
            Review savedReview = reviewService.addReview(review);
            logger.info("Successfully added review for productId: {} by userId: {}", review.getProduct().getId(), review.getUser().getId());
            return new ResponseEntity<>(savedReview, HttpStatus.CREATED);  // Successfully created review
        } catch (Exception e) {
            logger.error("Error adding review for productId: {} by userId: {}. Error: {}", review.getProduct().getId(), review.getUser().getId(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Handle unexpected errors
        }
    }

}
