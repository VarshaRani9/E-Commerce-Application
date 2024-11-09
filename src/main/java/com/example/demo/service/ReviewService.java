package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Review;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

import java.util.List;

@Service
public class ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Review> getReviewsByProductId(Long productId) {
        logger.info("Fetching reviews for product with ID: {}", productId);
        try {
            List<Review> reviews = reviewRepository.findByProductId(productId);
            if (reviews.isEmpty()) {
                logger.warn("No reviews found for product with ID: {}", productId);
            } else {
                logger.info("Found {} reviews for product with ID: {}", reviews.size(), productId);
            }
            return reviews;
        } catch (Exception e) {
            logger.error("Error occurred while fetching reviews for product with ID: {}", productId, e);
            throw new RuntimeException("Error fetching reviews", e);
        }
    }

    public Review addReview(Review review) {
        logger.info("Adding review for product with ID: {}", review.getProduct().getId());

        try {
            // Check if the user and product exist before saving the review
            User user = userRepository.findById(review.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User with ID " + review.getUser().getId() + " not found"));
            logger.info("User found: {}", user.getId());

            Product product = productRepository.findById(review.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product with ID " + review.getProduct().getId() + " not found"));
            logger.info("Product found: {}", product.getId());

            // Set the correct user and product to the review before saving
            review.setUser(user);
            review.setProduct(product);

            Review savedReview = reviewRepository.save(review);
            logger.info("Review added successfully for product with ID: {}", product.getId());

            return savedReview;
        } catch (Exception e) {
            logger.error("Error occurred while adding review for product with ID: {}", review.getProduct().getId(), e);
            throw new RuntimeException("Error adding review", e);
        }
    }
}
