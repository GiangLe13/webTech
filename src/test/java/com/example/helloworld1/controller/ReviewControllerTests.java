package com.example.helloworld1.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.example.helloworld1.persistence.Restaurant;
import com.example.helloworld1.persistence.Review;
import com.example.helloworld1.persistence.attribute.*;
import com.example.helloworld1.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService service;

    @Test
    @DisplayName("should fetch all reviews")
    public void testGetAllReviews() throws Exception {
        List<Review> reviews = Arrays.asList(
                new Review(new Restaurant("R1", "D1", "A1", Category.ITALIAN), "Author1", Rating.GOOD, "Comment1"),
                new Review(new Restaurant("R2", "D2", "A2", Category.CHINESE), "Author2", Rating.GOOD, "Comment2")
        );

        when(service.getAll()).thenReturn(reviews);

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].author").value("Author1"))
                .andExpect(jsonPath("$[1].author").value("Author2"));
    }


    @Test
    @DisplayName("should get review by ID")
    public void testGetReviewById() throws Exception {
        Review review = new Review(new Restaurant("R1", "D1", "A1", Category.ITALIAN), "Author1", Rating.GOOD, "Comment1");
        review.setId(1L);

        when(service.get(1L)).thenReturn(review);

        mockMvc.perform(get("/reviews/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.author").value("Author1"))
                .andExpect(jsonPath("$.rating").value("GOOD"));
    }

    @Test
    @DisplayName("should save review")
    public void testSaveReview() throws Exception {
        Review review = new Review(new Restaurant("R1", "D1", "A1", Category.ITALIAN), "Author1", Rating.GOOD, "Comment1");

        when(service.save(any(Review.class))).thenReturn(review);

        ObjectMapper objectMapper = new ObjectMapper();
        String reviewJson = objectMapper.writeValueAsString(review);

        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Author1"))
                .andExpect(jsonPath("$.rating").value("GOOD"));
    }

    @Test
    @DisplayName("should delete review and return 200 OK")
    void testDeleteReview() throws Exception {
        Long reviewId = 1L;
        doNothing().when(service).delete(reviewId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/reviews/" + reviewId))
                .andExpect(status().isOk());

        verify(service).delete(reviewId);
    }
}
