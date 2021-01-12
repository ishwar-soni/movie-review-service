package com.inos.mrs.daos;

import com.inos.mrs.entities.Movie;
import com.inos.mrs.exceptions.MovieNotFoundException;
import com.inos.mrs.utils.IdCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieDao {
    private final List<Movie> movies;

    public MovieDao() {
        this.movies = new ArrayList<>();
    }

    public Movie create(Movie movie) {
        movie.setId(IdCounter.getId());
        movies.add(movie);
        return movie;
    }

    public Movie findByName(String name) throws MovieNotFoundException {
        return movies
                .stream()
                .filter(movie -> movie.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new MovieNotFoundException("Movie with movie name: [" + name + "] not found."));
    }

    public Movie findById(int id) throws MovieNotFoundException {
        return movies
                .stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new MovieNotFoundException("Movie with movie id: [" + id + "] not found."));
    }

    public List<Movie> findByIds(List<Integer> ids) throws MovieNotFoundException {
        return movies
                .stream()
                .filter(movie -> ids.contains(movie.getId()))
                .collect(Collectors.toList());
    }

    public List<Movie> findAll() {
        return movies;
    }
}
