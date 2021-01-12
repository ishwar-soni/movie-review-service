package com.inos.mrs;

import com.inos.mrs.daos.MovieDao;
import com.inos.mrs.daos.ReviewDao;
import com.inos.mrs.daos.UserDao;
import com.inos.mrs.exceptions.MovieNotFoundException;
import com.inos.mrs.exceptions.UserNotFoundException;
import com.inos.mrs.services.MovieReviewService;
import com.inos.mrs.utils.Genre;
import com.inos.mrs.utils.Role;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws MovieNotFoundException, UserNotFoundException {
        MovieDao movieDao = new MovieDao();
        ReviewDao reviewDao = new ReviewDao();
        UserDao userDao = new UserDao();
        MovieReviewService movieReviewService = new MovieReviewService(movieDao, reviewDao, userDao);

        movieReviewService.addMovie("Don", 2006, Genre.ACTION, Genre.COMEDY);
        movieReviewService.addMovie("Tiger", 2008, Genre.DRAMA);
        movieReviewService.addMovie("Padmaavat", 2006, Genre.COMEDY);
        movieReviewService.addMovie("Lunchbox", 2021, Genre.DRAMA);
        movieReviewService.addMovie("Guru", 2006, Genre.DRAMA);
        movieReviewService.addMovie("Metro", 2006, Genre.ROMANCE);

        movieReviewService.addUser("SRK");
        movieReviewService.addUser("Salman");
        movieReviewService.addUser("Deepika");

        movieReviewService.addReview("SRK", "Don", 2);
        movieReviewService.addReview("SRK", "Padmaavat", 8);
        movieReviewService.addReview("Salman", "Don", 5);
        movieReviewService.addReview("Deepika", "Don", 9);
        movieReviewService.addReview("Deepika", "Guru", 6);
        movieReviewService.addReview("SRK", "Don", 10);
        movieReviewService.addReview("Deepika", "Lunchbox", 5);
        movieReviewService.addReview("SRK", "Tiger", 5);
        movieReviewService.addReview("SRK", "Metro", 7);

        System.out.println(
                movieReviewService.getTopMoviesInYear(1, Role.VIEWER, LocalDate.of(2006, 1, 1))
        );

        System.out.println(
                movieReviewService.getTopMoviesInYear(1, Role.CRITIC, LocalDate.of(2006, 1, 1))
        );

        System.out.println(
                movieReviewService.getTopMoviesByGenre(1, Role.VIEWER, Genre.DRAMA)
        );

        System.out.println(
                movieReviewService.getAverageByYear(LocalDate.of(2006, 1, 1))
        );
    }
}
