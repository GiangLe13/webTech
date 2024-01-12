package com.example.helloworld1.service;


import com.example.helloworld1.persistence.Restaurant;
import com.example.helloworld1.persistence.Review;
import com.example.helloworld1.persistence.attribute.*;
import com.example.helloworld1.repository.ReviewRepository;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ReviewServiceTests {

    @Mock
    private ReviewRepository repo;

    @InjectMocks
    private ReviewService service;

    @Test
    @DisplayName("should fetch all reviews")
    public void testGetAllReviews() {
        List<Review> mockReviews = Arrays.asList(
                new Review(new Restaurant("R1", "D1", "A1", Category.ITALIAN), "Author1", Rating.GOOD, "Comment1"),
                new Review(new Restaurant("R2", "D2", "A2", Category.CHINESE), "Author2", Rating.GOOD, "Comment2")
        );

        when(repo.findAll()).thenReturn(mockReviews);

        List<Review> retrievedReviews = service.getAll();
        assertNotNull(retrievedReviews);
        assertEquals(2, retrievedReviews.size());
        verify(repo).findAll();
    }

    @Test
    @DisplayName("should fetch  review by ID")
    public void testGetReviewById() {
        Long reviewId = 1L;
        Review mockReview = new Review(new Restaurant("R1", "D1", "A1", Category.ITALIAN), "Author1", Rating.GOOD, "Comment1");
        mockReview.setId(reviewId);

        when(repo.findById(reviewId)).thenReturn(Optional.of(mockReview));

        Review retrievedReview = service.get(reviewId);
        assertNotNull(retrievedReview);
        assertEquals("Author1", retrievedReview.getAuthor());
        verify(repo).findById(reviewId);
    }

    @Test
    @DisplayName("should save review")
    public void testSaveReview() {
        Review newReview = new Review(new Restaurant("R1", "D1", "A1", Category.ITALIAN), "Author1", Rating.GOOD, "Comment1");

        when(repo.save(any(Review.class))).thenReturn(newReview);

        Review savedReview = service.save(newReview);
        assertNotNull(savedReview);
        assertEquals("Author1", savedReview.getAuthor());
        verify(repo).save(any(Review.class));
    }

}
