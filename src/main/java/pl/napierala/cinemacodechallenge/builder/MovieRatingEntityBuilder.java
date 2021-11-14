package pl.napierala.cinemacodechallenge.builder;

import pl.napierala.cinemacodechallenge.entity.MovieEntity;
import pl.napierala.cinemacodechallenge.entity.MovieRatingEntity;
import pl.napierala.cinemacodechallenge.entity.UserEntity;

public class MovieRatingEntityBuilder {

    public static MovieRatingEntity buildWith(MovieEntity movieEntity, UserEntity userEntity, Integer rating) {
        return MovieRatingEntity.builder()
                .movie(movieEntity)
                .user(userEntity)
                .rating(rating)
                .build();
    }
}