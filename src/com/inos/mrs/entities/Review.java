package com.inos.mrs.entities;

import com.inos.mrs.utils.Role;

public class Review {
    private Integer id;
    private User user;
    private Movie movie;
    private int score;
    private Role userRole;

    public Review(User user, Movie movie, int score, Role userRole) {
        this.user = user;
        this.movie = movie;
        this.score = score;
        this.userRole = userRole;
    }

    public Review(Review review) {
        this.id = review.getId();
        this.user = review.getUser();
        this.movie = review.getMovie();
        this.score = review.getScore();
        this.userRole = review.getUserRole();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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
                ", user=" + user +
                ", movie=" + movie +
                ", score=" + score +
                ", userRole=" + userRole +
                '}';
    }
}
