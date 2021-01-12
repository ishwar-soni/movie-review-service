package com.inos.mrs.daos;

import com.inos.mrs.entities.Movie;
import com.inos.mrs.entities.Review;
import com.inos.mrs.entities.User;
import com.inos.mrs.utils.IdCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewDao {
    private final List<Review> reviews;

    public ReviewDao() {
        this.reviews = new ArrayList<>();
    }

    public Review create(Review review) {
        review.setId(IdCounter.getId());
        reviews.add(review);
        return review;
    }

    public List<Review> findByUser(User user) {
        return reviews
                .stream()
                .filter(review -> review.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    public List<Review> findAll() {
        return reviews;
    }

    public List<Review> findByMovie(Movie movie) {
        return reviews
                .stream()
                .filter(review -> review.getMovie().getId().equals(movie.getId()))
                .collect(Collectors.toList());
    }
}
