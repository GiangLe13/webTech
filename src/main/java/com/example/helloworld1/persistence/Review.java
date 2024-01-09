package com.example.helloworld1.persistence;

import com.example.helloworld1.persistence.attribute.Rating;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;

    @Enumerated(value = EnumType.ORDINAL)
    private Rating rating;

    private String comment;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Review() {
    }

    public Review(Restaurant restaurant, String author, Rating rating, String comment) {
        this.author = author;
        this.rating = rating;
        this.comment = comment;
        this.restaurant = restaurant;
    }

    //public void setId(Long id) {this.id = id;}

    public void setAuthor(String reviewer) {
        this.author = reviewer;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", reviewerName='" + author + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", restaurant=" + restaurant +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review review)) return false;
        return getRating() == review.getRating() && Objects.equals(getId(), review.getId()) && Objects.equals(getAuthor(), review.getAuthor()) && Objects.equals(getComment(), review.getComment()) && Objects.equals(getRestaurant(), review.getRestaurant());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthor(), getRating(), getComment(), getRestaurant());
    }
}
