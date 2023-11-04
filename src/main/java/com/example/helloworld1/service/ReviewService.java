package com.example.helloworld1.service;

import com.example.helloworld1.persistence.Review;
import com.example.helloworld1.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repo;

    public List<Review> getAll() {
        return repo.findAll();
    }

    public Review get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }

    public Review save(Review review) {
        if (review.getRestaurant() == null) {
            throw new IllegalArgumentException("Restaurant field cannot be empty");
        }

        if (review.getRating() == null || review.getRating().ordinal() > 5) {
            throw new IllegalArgumentException("Rating field cannot be empty");
        }

        return repo.save(review);
    }

    public Review update(Long id, Review update) {
        Review review = repo.findById(id).orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        review.setAuthor(update.getAuthor());
        review.setRestaurant(update.getRestaurant());
        review.setRating(update.getRating());
        review.setComment(update.getComment());

        return repo.save(review);
    }

    public void delete(Long id) {
        Review review = repo.findById(id).orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        repo.delete(review);
    }
}
