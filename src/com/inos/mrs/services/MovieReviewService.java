package com.inos.mrs.services;

import com.inos.mrs.daos.MovieDao;
import com.inos.mrs.daos.ReviewDao;
import com.inos.mrs.daos.UserDao;
import com.inos.mrs.entities.Movie;
import com.inos.mrs.entities.Review;
import com.inos.mrs.entities.User;
import com.inos.mrs.exceptions.MovieNotFoundException;
import com.inos.mrs.exceptions.UserNotFoundException;
import com.inos.mrs.utils.Genre;
import com.inos.mrs.utils.Role;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MovieReviewService {
    private final MovieDao movieDao;
    private final ReviewDao reviewDao;
    private final UserDao userDao;

    public MovieReviewService(MovieDao movieDao, ReviewDao reviewDao, UserDao userDao) {
        this.movieDao = movieDao;
        this.reviewDao = reviewDao;
        this.userDao = userDao;
    }

    public User addUser(String name) {
        User user = new User(name);
        return userDao.create(user);
    }

    public Movie addMovie(String name, int year, Genre... genres) {
        Movie movie = new Movie(
                name,
                LocalDate.of(year, 1, 1),
                Arrays.stream(genres).collect(Collectors.toList()));
        return movieDao.create(movie);
    }

    public Review addReview(String userName, String movieName, int score)
            throws UserNotFoundException, MovieNotFoundException {
        User user = userDao.findByName(userName);
        Movie movie = movieDao.findByName(movieName);
        Review review = new Review(user.getId(), movie.getId(), score, user.getRole());

        Review result = reviewDao.create(review);

        List<Review> reviews = reviewDao.findByUserId(user.getId());
        if (reviews.size() >= 3) {
            user.setRole(Role.CRITIC);
            userDao.update(user);
        }

        return result;
    }

    public List<Movie> getTopMoviesInYear(int n, Role role, LocalDate releasedDate) throws MovieNotFoundException {
        Map<Integer, Integer> moviesMap = reviewDao
                .findAll()
                .stream()
                .filter(review -> review.getUserRole() == role)
                .collect(Collectors.toMap(Review::getMovieId, Review::getScore, Integer::sum));

        List<Integer> movieIds = new ArrayList<>(moviesMap.keySet());

        List<Movie> movies = movieDao
                .findByIds(movieIds)
                .stream()
                .filter(movie -> movie.getReleasedDate().getYear() == releasedDate.getYear())
                .collect(Collectors.toList());

        List<Map.Entry<Integer, Integer>> sortedMap =
                new ArrayList<>(moviesMap.entrySet())
                        .stream()
                        .sorted((a, b) -> -a.getValue() + b.getValue())
                        .collect(Collectors.toList());

        List<Movie> result = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry: sortedMap) {
            Movie temp = movies
                    .stream()
                    .filter(movie -> movie.getId().equals(entry.getKey()))
                    .findFirst()
                    .orElse(null);
            if (temp != null) {
                result.add(temp);
                n--;
            }
            if (n == 0) break;
        }

        return result;
    }

    public List<Movie> getTopMoviesByGenre(int n, Role role, Genre genre) throws MovieNotFoundException {
        Map<Integer, Integer> moviesMap = reviewDao
                .findAll()
                .stream()
                .filter(review -> review.getUserRole() == role)
                .collect(Collectors.toMap(Review::getMovieId, Review::getScore, Integer::sum));

        List<Integer> movieIds = new ArrayList<>(moviesMap.keySet());

        List<Movie> movies = movieDao
                .findByIds(movieIds)
                .stream()
                .filter(movie -> movie.getGeneres().contains(genre))
                .collect(Collectors.toList());

        List<Map.Entry<Integer, Integer>> sortedMap =
                new ArrayList<>(moviesMap.entrySet())
                        .stream()
                        .sorted((a, b) -> -a.getValue() + b.getValue())
                        .collect(Collectors.toList());

        List<Movie> result = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry: sortedMap) {
            Movie temp = movies
                    .stream()
                    .filter(movie -> movie.getId().equals(entry.getKey()))
                    .findFirst()
                    .orElse(null);
            if (temp != null) {
                result.add(temp);
                n--;
            }
            if (n == 0) break;
        }

        return result;
    }

    public double getAverageByYear(LocalDate localDate) throws MovieNotFoundException {
        List<Movie> movies = movieDao
                .findAll()
                .stream()
                .filter(movie -> movie.getReleasedDate().getYear() == localDate.getYear())
                .collect(Collectors.toList());

        int sum = 0;
        int count = 0;

        for (Movie movie: movies) {
            List<Review> reviews = reviewDao.findByMovieId(movie.getId());
            sum += reviews
                    .stream()
                    .map(review -> review.getUserRole()==Role.CRITIC ? 2*review.getScore(): review.getScore())
                    .reduce(0, Integer::sum);
            count += reviews.size();
        }

        return sum * 1.0 / count;
    }

    public double getAverageByGenre(Genre genre) throws MovieNotFoundException {
        List<Movie> movies = movieDao
                .findAll()
                .stream()
                .filter(movie -> movie.getGeneres().contains(genre))
                .collect(Collectors.toList());

        int sum = 0;
        int count = 0;

        for (Movie movie: movies) {
            List<Review> reviews = reviewDao.findByMovieId(movie.getId());
            sum += reviews.stream()
                    .map(review -> review.getUserRole()==Role.CRITIC ? 2*review.getScore(): review.getScore())
                    .reduce(0, Integer::sum);
            count += reviews.size();
        }

        return sum * 1.0 / count;
    }

    public double getAverageByMovie(String name) throws MovieNotFoundException {
        List<Movie> movies = movieDao
                .findAll()
                .stream()
                .filter(movie -> movie.getName().equals(name))
                .collect(Collectors.toList());

        int sum = 0;
        int count = 0;

        for (Movie movie: movies) {
            List<Review> reviews = reviewDao.findByMovieId(movie.getId());
            sum += reviews.stream()
                    .map(review -> review.getUserRole()==Role.CRITIC ? 2*review.getScore(): review.getScore())
                    .reduce(0, Integer::sum);
            count += reviews.size();
        }

        return sum * 1.0 / count;
    }
}
