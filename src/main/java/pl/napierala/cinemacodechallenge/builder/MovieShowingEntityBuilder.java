package pl.napierala.cinemacodechallenge.builder;

import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.MovieEntity;
import pl.napierala.cinemacodechallenge.entity.MovieShowingEntity;
import pl.napierala.cinemacodechallenge.extmodel.AddMovieShowingRequest;

import java.util.UUID;

public class MovieShowingEntityBuilder {

    public static MovieShowingEntity buildWith(CinemaEntity cinemaEntity, MovieEntity movieEntity, AddMovieShowingRequest request) {

        return MovieShowingEntity.builder()
                .cinema(cinemaEntity)
                .movie(movieEntity)
                .uuid(UUID.randomUUID().toString())
                .dateTime(request.getDateTime())
                .room(request.getRoom())
                .build();
    }
}