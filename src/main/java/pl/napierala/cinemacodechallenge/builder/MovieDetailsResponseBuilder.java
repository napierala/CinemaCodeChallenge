package pl.napierala.cinemacodechallenge.builder;

import pl.napierala.cinemacodechallenge.entity.MovieEntity;
import pl.napierala.cinemacodechallenge.entity.MovieRatingEntity;
import pl.napierala.cinemacodechallenge.extmodel.MovieDetailsResponse;
import pl.napierala.cinemacodechallenge.imdb.IMDBMovie;

import java.util.Optional;

public class MovieDetailsResponseBuilder {

    public static MovieDetailsResponse buildWith(MovieEntity movieEntity, Optional<MovieRatingEntity> movieRating, IMDBMovie imdbMovie) {

        Integer userRating = null;

        if (movieRating.isPresent()) {
            userRating = movieRating.get().getRating();
        }

        return MovieDetailsResponse.builder()
                .code(movieEntity.getCode())
                .name(imdbMovie.getTitle())
                .usersRating(userRating)
                .imdbRating(imdbMovie.getRating())
                .releasedDate(imdbMovie.getReleased())
                .runTimeInMinutes(imdbMovie.getRuntimeInMinutes())
                .build();
    }
}
