package com.inos.mrs.entities;

import com.inos.mrs.utils.Role;

public class Review {
    private Integer id;
    private int userId;
    private int movieId;
    private int score;
    private Role userRole;

    public Review(int userId, int movieId, int score, Role userRole) {
        this.userId = userId;
        this.movieId = movieId;
        this.score = score;
        this.userRole = userRole;
    }

    public Review(Review review) {
        this.id = review.getId();
        this.userId = review.getUserId();
        this.movieId = review.getMovieId();
        this.score = review.getScore();
        this.userRole = review.getUserRole();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", userId=" + userId +
                ", movieId=" + movieId +
                ", score=" + score +
                ", userRole=" + userRole +
                '}';
    }
}
