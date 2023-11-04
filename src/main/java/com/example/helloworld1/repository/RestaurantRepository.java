package com.example.helloworld1.repository;

import com.example.helloworld1.persistence.attribute.Category;
import com.example.helloworld1.persistence.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByDistrict(String district);
    List<Restaurant> findByName(String name);
    List<Restaurant> findByCategory(Category category);
    Restaurant findById(long id);

}
