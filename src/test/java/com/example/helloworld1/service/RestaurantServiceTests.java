package com.example.helloworld1.service;

import com.example.helloworld1.persistence.Restaurant;
import com.example.helloworld1.persistence.attribute.Category;
import com.example.helloworld1.repository.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTests {

    @Mock
    private RestaurantRepository repo;

    @InjectMocks
    private RestaurantService service;

    @Test
    @DisplayName("should return all restaurants")
    void should_return_all_restaurants() {
        List<Restaurant> restaurants = Arrays.asList(new Restaurant(), new Restaurant());
        when(repo.findAll()).thenReturn(restaurants);

        List<Restaurant> result = service.getAll();

        assertThat(result).hasSize(2);
        verify(repo).findAll();
    }

    @Test
    @DisplayName("should return restaurant by ID")
    void should_return_restaurant_by_id() {
        Long givenId = 1L;
        Restaurant restaurant = new Restaurant();
        when(repo.findById(givenId)).thenReturn(Optional.of(restaurant));

        Restaurant result = service.get(givenId);

        assertThat(result).isNotNull();
        verify(repo).findById(givenId);
    }

    @Test
    @DisplayName("should save and return the new restaurant")
    void should_save_and_return_new_restaurant() {
        Restaurant restaurant = new Restaurant("R1", "D1", "A1", Category.GREEK);
        when(repo.save(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant result = service.save(restaurant);

        assertThat(result).isNotNull();
        verify(repo).save(restaurant);
    }

    @Test
    @DisplayName("should update and return the updated restaurant")
    void should_update_and_return_updated_restaurant() {
        Long givenId = 1L;
        Restaurant existingRestaurant = new Restaurant();
        existingRestaurant.setId(givenId);
        Restaurant updatedRestaurant = new Restaurant("R1", "D1", "A1", Category.GREEK);
        updatedRestaurant.setId(givenId);
        when(repo.findById(givenId)).thenReturn(Optional.of(existingRestaurant));
        when(repo.save(any(Restaurant.class))).thenReturn(updatedRestaurant);

        Restaurant result = service.update(givenId, updatedRestaurant);

        assertThat(result).isNotNull();
        verify(repo).findById(givenId);
        verify(repo).save(updatedRestaurant);
    }

    @Test
    @DisplayName("should delete restaurant by ID")
    void should_delete_restaurant_by_id() {
        Long givenId = 1L;
        Restaurant restaurant = new Restaurant();
        when(repo.findById(givenId)).thenReturn(Optional.of(restaurant));

        service.delete(givenId);

        verify(repo).findById(givenId);
        verify(repo).delete(restaurant);
    }

    @Test
    @DisplayName("should return 'Restaurant name cannot be empty'")
    public void testSaveRestaurantInvalid() {
        Restaurant invalidRestaurant = new Restaurant("", "District", "Address", Category.ITALIAN);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.save(invalidRestaurant);
        });

        String expectedMessage = "Restaurant name cannot be empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
