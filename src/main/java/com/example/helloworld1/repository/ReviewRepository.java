package com.example.helloworld1.repository;

import com.example.helloworld1.persistence.Restaurant;
import com.example.helloworld1.persistence.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByRestaurant(Restaurant restaurant);
    Review findById (long id);
}
