package pl.napierala.cinemacodechallenge.builder;

import pl.napierala.cinemacodechallenge.entity.MovieShowingEntity;
import pl.napierala.cinemacodechallenge.extmodel.FindMovieShowingsResponse;
import pl.napierala.cinemacodechallenge.extmodel.MovieShowingResponse;

import java.util.List;
import java.util.stream.Collectors;

public class FindMovieShowingsResponseBuilder {

    public static FindMovieShowingsResponse buildWithList(List<MovieShowingEntity> input) {

        if (input == null) {
            return null;
        }

        List<MovieShowingResponse> result = input.stream()
                .map(MovieShowingResponseBuilder::buildWith)
                .collect(Collectors.toList());

        return FindMovieShowingsResponse.builder()
                .movieShowings(result)
                .build();
    }
}