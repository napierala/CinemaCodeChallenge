package pl.napierala.cinemacodechallenge.builder;

import org.junit.Test;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.extmodel.UpdateTicketPricesResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UpdateTicketPricesResponseBuilderTest {

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

        // When
        UpdateTicketPricesResponse result = UpdateTicketPricesResponseBuilder.buildWith(cinemaEntity);

        // Then
        assertNotNull(result);
        assertNotNull(result.getCinema());
        assertEquals(code, result.getCinema().getCode());
        assertEquals(name, result.getCinema().getName());
        assertEquals(address, result.getCinema().getAddress());
    }
}