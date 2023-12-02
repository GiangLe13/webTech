package com.example.helloworld1.controller;

import com.example.helloworld1.persistence.Restaurant;
import com.example.helloworld1.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    @GetMapping("/")
    public String index() {
        return "Restaurants!";
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public Restaurant saveRestaurant(@RequestBody Restaurant restaurant) {
        return service.save(restaurant);
    }

    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody Restaurant update) {
        return service.update(id, update);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        service.delete(id);
    }
}

