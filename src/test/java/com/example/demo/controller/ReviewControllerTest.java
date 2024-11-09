package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.entity.Review;
import com.example.demo.service.ReviewService;

@SpringBootTest
class ReviewControllerTest {

	@InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    private Review review;

    @BeforeEach
    public void setUp() {
        // Setup Review object before each test
        review = new Review();
        review.setId(1L);
        review.setRating(5);
        review.setComment("Great product!");
    }

    @Test
    public void testGetReviewsByProductId() {
        // Mock the service method to return a list of reviews
        when(reviewService.getReviewsByProductId(1L)).thenReturn(Arrays.asList(review));

        // Call the controller method
        ResponseEntity<List<Review>> response = reviewController.getReviewsByProductId(1L);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Great product!", response.getBody().get(0).getComment());

        // Verify the interaction with the service
        verify(reviewService, times(1)).getReviewsByProductId(1L);
    }

    
    @Test
    public void testAddReview_Success() {
        // Mock the service method to return the review
        when(reviewService.addReview(review)).thenReturn(review);

        // Call the controller method
        ResponseEntity<Review> response = reviewController.addReview(review);

        // Assert the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Great product!", response.getBody().getComment());

        // Verify the interaction with the service
        verify(reviewService, times(1)).addReview(review);
    }

    @Test
    public void testAddReview_InvalidRating() {
        // Create a review with an invalid rating
        review.setRating(6);  // Invalid rating (should be between 1 and 5)

        // Call the controller method
        ResponseEntity<Review> response = reviewController.addReview(review);

        // Assert the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

        // Verify the interaction with the service (should not be called)
        verify(reviewService, times(0)).addReview(review);
    }

    @Test
    public void testAddReview_InternalServerError() {
        // Mock the service method to throw an exception
        when(reviewService.addReview(review)).thenThrow(new RuntimeException("Error"));

        // Call the controller method
        ResponseEntity<Review> response = reviewController.addReview(review);

        // Assert the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        // Verify the interaction with the service
        verify(reviewService, times(1)).addReview(review);
    }
}
