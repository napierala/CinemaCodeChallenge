package pl.napierala.cinemacodechallenge.builder;

import com.google.common.collect.Lists;
import org.junit.Test;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.TicketPriceEntity;
import pl.napierala.cinemacodechallenge.extmodel.CinemaDetailsResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CinemaDetailsResponseBuilderTest {

    @Test
    public void shouldBuildCorrectly() {

        // Given
        String code = "CODE";
        String name = "NAME";
        String address = "ADDRESS";

        CinemaEntity cinemaEntity = CinemaEntity.builder()
                .code(code)
                .name(name)
                .address(address)
                .build();

        String description = "DESCRIPTION";
        Integer priceInCents = 100;

        TicketPriceEntity ticketPriceEntity = TicketPriceEntity.builder()
                .cinema(cinemaEntity)
                .description(description)
                .priceInCents(priceInCents)
                .build();

        // When
        CinemaDetailsResponse result = CinemaDetailsResponseBuilder.buildWith(cinemaEntity, Lists.newArrayList(ticketPriceEntity));

        // Then
        assertNotNull(result);
        assertEquals(code, result.getCode());
        assertEquals(name, result.getName());
        assertEquals(address, result.getAddress());

        assertNotNull(result.getTicketPrices());
        assertEquals(1, result.getTicketPrices().size());
        assertEquals(description, result.getTicketPrices().get(0).getDescription());
        assertEquals(priceInCents, result.getTicketPrices().get(0).getPriceInCents());
    }
}