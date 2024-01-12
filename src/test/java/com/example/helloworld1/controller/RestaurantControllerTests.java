package com.example.helloworld1.controller;

import com.example.helloworld1.persistence.Restaurant;
import com.example.helloworld1.persistence.attribute.Category;
import com.example.helloworld1.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService service;

    @Test
    @DisplayName("should fetch all restaurants and return 200 OK")
    public void testGetAllRestaurants() throws Exception {
        List<Restaurant> restaurants = Arrays.asList(
                new Restaurant("R1", "D1", "A1", Category.ITALIAN),
                new Restaurant("R2", "D2", "A2", Category.GREEK)
        );
        restaurants.get(0).setId(1L);
        restaurants.get(1).setId(2L);

        String expected = "[{\"id\":1,\"name\":\"R1\",\"district\":\"D1\",\"address\":\"A1\",\"category\":\"ITALIAN\",\"reviews\":null},{\"id\":2,\"name\":\"R2\",\"district\":\"D2\",\"address\":\"A2\",\"category\":\"GREEK\",\"reviews\":null}]";

        when(service.getAll()).thenReturn(restaurants);

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    @DisplayName("should fetch a restaurant and return 200 OK")
    public void testGetRestaurantById() throws Exception {
        Restaurant restaurant = new Restaurant("R1", "D1", "A1", Category.ITALIAN);
        restaurant.setId(1L);

        Mockito.when(service.get(1L)).thenReturn(restaurant);

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("R1"))
                .andExpect(jsonPath("$.district").value("D1"))
                .andExpect(jsonPath("$.address").value("A1"))
                .andExpect(jsonPath("$.category").value("ITALIAN"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("should save restaurant and return 200 OK")
    void testSaveRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant("R1", "D1", "A1", Category.ITALIAN);

        when(service.save(any(Restaurant.class))).thenReturn(restaurant);

        String restaurantJson = new ObjectMapper().writeValueAsString(restaurant);

        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("R1"))
                .andExpect(jsonPath("$.district").value("D1"))
                .andExpect(jsonPath("$.address").value("A1"))
                .andExpect(jsonPath("$.category").value("ITALIAN"));

        verify(service).save(any(Restaurant.class));
    }

    @Test
    @DisplayName("should delete restaurant and return 200 OK")
    void testDeleteRestaurant() throws Exception {
        Long restaurantId = 1L;
        doNothing().when(service).delete(restaurantId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/restaurants/" + restaurantId))
                .andExpect(status().isOk());

        verify(service).delete(restaurantId);
    }
}

