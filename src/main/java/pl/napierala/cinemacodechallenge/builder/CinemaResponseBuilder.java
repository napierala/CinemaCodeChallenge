package pl.napierala.cinemacodechallenge.builder;

import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.extmodel.CinemaResponse;

public class CinemaResponseBuilder {

    public static CinemaResponse buildWith(CinemaEntity input) {
        return CinemaResponse.builder()
                .code(input.getCode())
                .name(input.getName())
                .address(input.getAddress())
                .build();
    }
}