package com.inos.mrs.daos;

import com.inos.mrs.entities.Review;
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
        reviews.add(new Review(review));
        return review;
    }

    public List<Review> findByUserId(int userId) {
        List<Review> temp = reviews
                .stream()
                .filter(review -> review.getUserId() == userId)
                .collect(Collectors.toList());
        List<Review> result = new ArrayList<>();
        for (Review review: temp) {
            result.add(new Review(review));
        }
        return result;
    }

    public List<Review> findAll() {
        List<Review> result = new ArrayList<>();
        for (Review review: reviews) {
            result.add(new Review(review));
        }
        return result;
    }

    public List<Review> findByMovieId(int movieId) {
        List<Review> temp = reviews
                .stream()
                .filter(review -> review.getMovieId() == movieId)
                .collect(Collectors.toList());
        List<Review> result = new ArrayList<>();
        for (Review review: temp) {
            result.add(new Review(review));
        }
        return result;
    }
}
