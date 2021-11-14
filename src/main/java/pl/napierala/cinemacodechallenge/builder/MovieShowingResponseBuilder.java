package pl.napierala.cinemacodechallenge.builder;

import pl.napierala.cinemacodechallenge.entity.MovieShowingEntity;
import pl.napierala.cinemacodechallenge.extmodel.MovieShowingResponse;

public class MovieShowingResponseBuilder {

    public static MovieShowingResponse buildWith(MovieShowingEntity input) {

        return MovieShowingResponse.builder()
                .cinema(CinemaResponseBuilder.buildWith(input.getCinema()))
                .movie(BasicMovieResponseBuilder.buildWith(input.getMovie()))
                .uuid(input.getUuid())
                .dateTime(input.getDateTime())
                .room(input.getRoom())
                .build();
    }
}