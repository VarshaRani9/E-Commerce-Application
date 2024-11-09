package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Product;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;

@SpringBootTest
class ReviewServiceTest {

	@InjectMocks
    private ReviewService reviewService;  // Service we are testing

    @Mock
    private ReviewRepository reviewRepository;  // Mocked repository for reviews

    @Mock
    private ProductRepository productRepository;  // Mocked repository for products

    @Mock
    private UserRepository userRepository;  // Mocked repository for users

    private User user;
    private Product product;
    private Review review;

    @BeforeEach
    public void setup() {
        // Initialize user, product, and review for the tests
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");

        product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(100.0);

        review = new Review();
        review.setId(1L);
        review.setRating(5);
        review.setComment("Great product!");
        review.setUser(user);
        review.setProduct(product);
    }

    @Test
    public void testGetReviewsByProductId() {
        when(reviewRepository.findByProductId(anyLong())).thenReturn(Arrays.asList(review));

        List<Review> reviews = reviewService.getReviewsByProductId(1L);

        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals("Great product!", reviews.get(0).getComment());
        verify(reviewRepository, times(1)).findByProductId(anyLong());
    }

    @Test
    public void testAddReview_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review savedReview = reviewService.addReview(review);

        assertNotNull(savedReview);
        assertEquals("Great product!", savedReview.getComment());
        assertEquals(user, savedReview.getUser());
        assertEquals(product, savedReview.getProduct());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    public void testAddReview_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reviewService.addReview(review);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testAddReview_ProductNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reviewService.addReview(review);
        });

        assertEquals("Product not found", exception.getMessage());
    }

}
