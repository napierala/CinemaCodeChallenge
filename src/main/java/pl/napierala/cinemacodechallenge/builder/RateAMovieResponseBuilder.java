package pl.napierala.cinemacodechallenge.builder;

import pl.napierala.cinemacodechallenge.extmodel.RateAMovieResponse;

public class RateAMovieResponseBuilder {

    public static RateAMovieResponse buildWith(boolean successful) {
        return RateAMovieResponse.builder()
                .successful(successful)
                .build();
    }
}