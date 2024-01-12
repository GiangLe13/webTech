package com.example.helloworld1.persistence;

import com.example.helloworld1.persistence.attribute.Category;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTests {

    @Test
    void testRestaurantToString() {
        Restaurant restaurant = new Restaurant("R1", "D1", "A1", Category.ITALIAN);
        restaurant.setId(1L);

        String expected = "Restaurant{id=1, name='R1', district='D1', address='A1', category='ITALIAN'}";
        assertEquals(expected, restaurant.toString());
    }
}
