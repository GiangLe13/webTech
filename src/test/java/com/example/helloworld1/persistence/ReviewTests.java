package com.example.helloworld1.persistence;

import com.example.helloworld1.persistence.attribute.Category;
import com.example.helloworld1.persistence.attribute.Rating;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReviewTests {

    @Test
    void testReviewToString() {
        Restaurant restaurant = new Restaurant("Restaurant", "District", "Address", Category.ITALIAN);
        restaurant.setId(1L);
        Review review = new Review(restaurant, "Author", Rating.GOOD, "Great food");
        review.setId(2L);

        String expected = "Review{id=2, reviewerName='Author', rating=GOOD, comment='Great food', restaurant=Restaurant{id=1, name='Restaurant', district='District', address='Address', category='ITALIAN'}}";
        assertEquals(expected, review.toString());
    }
}

