package pl.napierala.cinemacodechallenge.builder;

import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.extmodel.UpdateTicketPricesResponse;

public class UpdateTicketPricesResponseBuilder {

    public static UpdateTicketPricesResponse buildWith(CinemaEntity cinemaEntity) {
        return UpdateTicketPricesResponse.builder()
                .cinema(CinemaResponseBuilder.buildWith(cinemaEntity))
                .build();
    }
}
