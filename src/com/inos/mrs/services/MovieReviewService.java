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
import java.util.stream.Stream;

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
        Review review = new Review(user, movie, score, user.getRole());

        Review result = reviewDao.create(review);

        List<Review> reviews = reviewDao.findByUser(user);
        if (reviews.size() >= 3) {
            user.setRole(Role.CRITIC);
            userDao.update(user);
        }

        return result;
    }

    public List<Map.Entry<Movie, Integer>> getTopMoviesInYear(int n, Role role, LocalDate releasedDate) throws MovieNotFoundException {
        return new ArrayList<>(reviewDao
                .findAll()
                .stream()
                .filter(review -> review.getUserRole() == role)
                .filter(review -> review.getMovie().getReleasedDate().getYear() == releasedDate.getYear())
                .collect(Collectors.toMap(
                        Review::getMovie,
                        review -> review.getUserRole()==Role.CRITIC ? 2*review.getScore() : review.getScore(),
                        Integer::sum))
                .entrySet())
                .stream()
                .sorted((a, b) -> -a.getValue() + b.getValue())
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<Map.Entry<Movie, Integer>> getTopMoviesByGenre(int n, Role role, Genre genre) throws MovieNotFoundException {
        return new ArrayList<>(reviewDao
                .findAll()
                .stream()
                .filter(review -> review.getUserRole() == role)
                .filter(review -> review.getMovie().getGenres().contains(genre))
                .collect(Collectors.toMap(
                        Review::getMovie,
                        review -> review.getUserRole()==Role.CRITIC ? 2*review.getScore() : review.getScore(),
                        Integer::sum))
                .entrySet())
                .stream()
                .sorted((a, b) -> -a.getValue() + b.getValue())
                .limit(n)
                .collect(Collectors.toList());
    }

    public double getAverageByYear(LocalDate localDate) throws MovieNotFoundException {

        int sum = reviewDao
                .findAll()
                .stream()
                .filter(review -> review.getMovie().getReleasedDate().getYear() == localDate.getYear())
                .map(review -> review.getUserRole()==Role.CRITIC ? 2*review.getScore() : review.getScore())
                .reduce(0, Integer::sum);

        long count = reviewDao
                .findAll()
                .stream()
                .filter(review -> review.getMovie().getReleasedDate().getYear() == localDate.getYear())
                .count();

        return sum * 1.0 / count;
    }

    public double getAverageByGenre(Genre genre) throws MovieNotFoundException {
        Stream<Review> reviews = reviewDao
                .findAll()
                .stream()
                .filter(review -> review.getMovie().getGenres().contains(genre));

        int sum = reviews
                .map(review -> review.getUserRole()==Role.CRITIC ? 2*review.getScore() : review.getScore())
                .reduce(0, Integer::sum);

        long count = reviews.count();

        return sum * 1.0 / count;
    }

    public double getAverageByMovie(String name) throws MovieNotFoundException {
        Stream<Review> reviews = reviewDao
                .findAll()
                .stream()
                .filter(review -> review.getMovie().getName().equals(name));

        int sum = reviews
                .map(review -> review.getUserRole()==Role.CRITIC ? 2*review.getScore() : review.getScore())
                .reduce(0, Integer::sum);

        long count = reviews.count();

        return sum * 1.0 / count;
    }
}
