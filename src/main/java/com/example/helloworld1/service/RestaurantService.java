package com.example.helloworld1.service;

import com.example.helloworld1.persistence.Restaurant;
import com.example.helloworld1.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository repo;

    public List<Restaurant> getAll() {
        return repo.findAll();
    }

    public Restaurant get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }

    public Restaurant save(Restaurant restaurant) {
        if (restaurant.getName() == null || restaurant.getName().isEmpty()) {
            throw new IllegalArgumentException("Restaurant name cannot be empty");
        }

        if (restaurant.getDistrict() == null || restaurant.getDistrict().isEmpty()) {
            throw new IllegalArgumentException("Restaurant district cannot be empty");
        }

        if (restaurant.getAddress() == null || restaurant.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Restaurant address cannot be empty");
        }

        if (restaurant.getCategory() == null) {
            throw new IllegalArgumentException("Restaurant category cannot be empty");
        }
        return repo.save(restaurant);
    }

    public Restaurant update(Long id, Restaurant update) {
        Restaurant restaurant = repo.findById(id).orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));

        restaurant.setName(update.getName());
        restaurant.setDistrict(update.getDistrict());
        restaurant.setAddress(update.getAddress());
        restaurant.setCategory(update.getCategory());

        return repo.save(restaurant);
    }

    public void delete(Long id) {
        Restaurant restaurant = repo.findById(id).orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));

        repo.delete(restaurant);
    }
}
