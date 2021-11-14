package pl.napierala.cinemacodechallenge.builder;

import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.TicketPriceEntity;
import pl.napierala.cinemacodechallenge.extmodel.TicketPricesRequest;

import java.util.List;
import java.util.stream.Collectors;

public class TicketPriceEntityBuilder {

    public static List<TicketPriceEntity> buildWithList(List<TicketPricesRequest> input, CinemaEntity cinemaEntity) {

        if (input == null) {
            return null;
        }

        return input.stream()
                .map(i -> buildWith(i, cinemaEntity))
                .collect(Collectors.toList());
    }

    public static TicketPriceEntity buildWith(TicketPricesRequest input, CinemaEntity cinemaEntity) {
        return TicketPriceEntity.builder()
                .cinema(cinemaEntity)
                .description(input.getDescription())
                .priceInCents(input.getPriceInCents())
                .build();
    }
}