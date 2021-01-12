package com.inos.mrs.daos;

import com.inos.mrs.entities.Movie;
import com.inos.mrs.exceptions.MovieNotFoundException;
import com.inos.mrs.utils.IdCounter;

import java.util.ArrayList;
import java.util.List;

public class MovieDao {
    private final List<Movie> movies;

    public MovieDao() {
        this.movies = new ArrayList<>();
    }

    public Movie create(Movie movie) {
        movie.setId(IdCounter.getId());
        movies.add(new Movie(movie));
        return movie;
    }

    public Movie findByName(String name) throws MovieNotFoundException {
        Movie result = movies
                .stream()
                .filter(movie -> movie.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new MovieNotFoundException("Movie with movie name: [" + name + "] not found."));
        return new Movie(result);
    }

    public Movie findById(int id) throws MovieNotFoundException {
        Movie movie = movies
                .stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new MovieNotFoundException("Movie with movie id: [" + id + "] not found."));
        return new Movie(movie);
    }

    public List<Movie> findByIds(List<Integer> ids) throws MovieNotFoundException {
        List<Movie> result = new ArrayList<>();
        for (int id: ids) {
            result.add(findById(id));
        }
        return result;
    }

    public List<Movie> findAll() {
        List<Movie> result = new ArrayList<>();
        for (Movie movie: movies) {
            result.add(new Movie(movie));
        }
        return result;
    }
}
