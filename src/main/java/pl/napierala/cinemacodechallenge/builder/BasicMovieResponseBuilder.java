package pl.napierala.cinemacodechallenge.builder;

import pl.napierala.cinemacodechallenge.entity.MovieEntity;
import pl.napierala.cinemacodechallenge.extmodel.BasicMovieResponse;

public class BasicMovieResponseBuilder {

    public static BasicMovieResponse buildWith(MovieEntity movie) {
        return BasicMovieResponse.builder()
                .code(movie.getCode())
                .name(movie.getName())
                .build();
    }
}