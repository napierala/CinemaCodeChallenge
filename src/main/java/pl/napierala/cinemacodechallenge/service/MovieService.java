package pl.napierala.cinemacodechallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.napierala.cinemacodechallenge.builder.MovieDetailsResponseBuilder;
import pl.napierala.cinemacodechallenge.builder.MovieRatingEntityBuilder;
import pl.napierala.cinemacodechallenge.builder.RateAMovieResponseBuilder;
import pl.napierala.cinemacodechallenge.entity.MovieEntity;
import pl.napierala.cinemacodechallenge.entity.MovieRatingEntity;
import pl.napierala.cinemacodechallenge.entity.UserEntity;
import pl.napierala.cinemacodechallenge.exception.MovieNotFoundException;
import pl.napierala.cinemacodechallenge.extmodel.MovieDetailsRequest;
import pl.napierala.cinemacodechallenge.extmodel.MovieDetailsResponse;
import pl.napierala.cinemacodechallenge.extmodel.RateAMovieRequest;
import pl.napierala.cinemacodechallenge.extmodel.RateAMovieResponse;
import pl.napierala.cinemacodechallenge.imdb.IMDBMovie;
import pl.napierala.cinemacodechallenge.repository.MovieRatingRepository;
import pl.napierala.cinemacodechallenge.repository.MovieRepository;

import java.util.Optional;

@Service
public class MovieService {

    private MovieRepository movieRepository;
    private MovieRatingRepository movieRatingRepository;
    private OMDBService omdbService;
    private UserService userService;

    @Autowired
    public MovieService(MovieRepository movieRepository, MovieRatingRepository movieRatingRepository,
                         OMDBService omdbService, UserService userService) {
        this.movieRepository = movieRepository;
        this.movieRatingRepository = movieRatingRepository;
        this.omdbService = omdbService;
        this.userService = userService;
    }

    public Optional<MovieEntity> findByCode(String code) {
        return movieRepository.findByCode(code);
    }

    public MovieEntity findByCodeOrThrowException(String code) {
        return movieRepository.findByCode(code).orElseThrow(MovieNotFoundException::new);
    }

    public Optional<MovieRatingEntity> findByMovieAndUser(MovieEntity movie, UserEntity user) {
        return movieRatingRepository.findByMovieAndUser(movie, user);
    }

    public MovieDetailsResponse details(MovieDetailsRequest request, String userName) {

        MovieEntity movieEntity = findByCodeOrThrowException(request.getMovieCode());

        UserEntity userEntity = userService.findByUserNameOrThrowException(userName);

        Optional<MovieRatingEntity> movieRating = movieRatingRepository.findByMovieAndUser(movieEntity, userEntity);

        IMDBMovie imdbMovie = omdbService.fetch(movieEntity.getImdbId());

        return MovieDetailsResponseBuilder.buildWith(movieEntity, movieRating, imdbMovie);
    }

    public RateAMovieResponse rate(RateAMovieRequest request, String userName) {

        MovieEntity movieEntity = findByCodeOrThrowException(request.getMovieCode());

        UserEntity userEntity = userService.findByUserNameOrThrowException(userName);

        if (movieRatingRepository.findByMovieAndUser(movieEntity, userEntity).isPresent()) {
            return RateAMovieResponseBuilder.buildWith(false);
        }

        MovieRatingEntity movieRatingEntity = MovieRatingEntityBuilder.buildWith(movieEntity, userEntity, request.getRating());

        movieRatingRepository.save(movieRatingEntity);

        return RateAMovieResponseBuilder.buildWith(true);
    }
}