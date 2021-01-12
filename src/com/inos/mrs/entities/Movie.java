package com.inos.mrs.entities;

import com.inos.mrs.utils.Genre;

import java.time.LocalDate;
import java.util.List;

public class Movie {
    private Integer id;
    private String name;
    private LocalDate releasedDate;
    private List<Genre> genres;

    public Movie(String name, LocalDate releasedDate, List<Genre> genres) {
        this.name = name;
        this.releasedDate = releasedDate;
        this.genres = genres;
    }

    public Movie(Movie movie) {
        this.id = movie.getId();
        this.name = movie.getName();
        this.releasedDate = movie.getReleasedDate();
        this.genres = movie.getGenres();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(LocalDate releasedDate) {
        this.releasedDate = releasedDate;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releasedDate=" + releasedDate +
                ", genres=" + genres +
                '}';
    }
}
