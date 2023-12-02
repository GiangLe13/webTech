package com.example.helloworld1.controller;

import com.example.helloworld1.persistence.Review;
import com.example.helloworld1.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;

    Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @GetMapping("/")
    public String index() {
        return "Reviews!";
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public Review saveReview(@RequestBody Review review) {
        return service.save(review);
    }

    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review update) {
        return service.update(id, update);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        service.delete(id);
    }

}