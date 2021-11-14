package pl.napierala.cinemacodechallenge.builder;

import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.TicketPriceEntity;
import pl.napierala.cinemacodechallenge.extmodel.CinemaDetailsResponse;
import pl.napierala.cinemacodechallenge.extmodel.TicketPricesResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CinemaDetailsResponseBuilder {

    public static CinemaDetailsResponse buildWith(CinemaEntity cinema, List<TicketPriceEntity> ticketPriceEntities) {

        return CinemaDetailsResponse.builder()
                .code(cinema.getCode())
                .name(cinema.getName())
                .address(cinema.getAddress())
                .ticketPrices(buildTicketPricesWithList(ticketPriceEntities))
                .build();
    }

    private static List<TicketPricesResponse> buildTicketPricesWithList(List<TicketPriceEntity> input) {

        if (input == null) {
            return null;
        }

        return input.stream()
                .map(CinemaDetailsResponseBuilder::buildWith)
                .collect(Collectors.toList());
    }

    private static TicketPricesResponse buildWith(TicketPriceEntity input) {

        return TicketPricesResponse.builder()
                .description(input.getDescription())
                .priceInCents(input.getPriceInCents())
                .build();
    }
}