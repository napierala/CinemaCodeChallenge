package pl.napierala.cinemacodechallenge.builder;

import com.google.common.collect.Lists;
import org.junit.Test;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.TicketPriceEntity;
import pl.napierala.cinemacodechallenge.extmodel.TicketPricesRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TicketPriceEntityBuilderTest {

    @Test
    public void shouldBuildCorrectlyForNull() {

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
        List<TicketPriceEntity> result = TicketPriceEntityBuilder.buildWithList(null, cinemaEntity);

        // Then
        assertNull(result);
    }

    @Test
    public void shouldBuildCorrectlyForEmpty() {

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
        List<TicketPriceEntity> result = TicketPriceEntityBuilder.buildWithList(new ArrayList<>(), cinemaEntity);

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void shouldBuildCorrectlyForOneElement() {

        // Given
        String code = "CODE";
        String name = "NAME";
        String address = "ADDRESS";

        CinemaEntity cinemaEntity = CinemaEntity.builder()
                .code(code)
                .name(name)
                .address(address)
                .build();

        String description = "TEST";
        Integer priceInCents = 100;

        TicketPricesRequest request = TicketPricesRequest.builder()
                .description(description)
                .priceInCents(priceInCents)
                .build();

        // When
        List<TicketPriceEntity> result = TicketPriceEntityBuilder.buildWithList(Lists.newArrayList(request), cinemaEntity);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(description, result.get(0).getDescription());
        assertEquals(priceInCents, result.get(0).getPriceInCents());
        assertEquals(cinemaEntity, result.get(0).getCinema());
    }

    @Test
    public void shouldBuildCorrectlyForTwoElement() {

        // Given
        String code = "CODE";
        String name = "NAME";
        String address = "ADDRESS";

        CinemaEntity cinemaEntity = CinemaEntity.builder()
                .code(code)
                .name(name)
                .address(address)
                .build();

        String description1 = "TEST";
        Integer priceInCents1 = 100;

        TicketPricesRequest request1 = TicketPricesRequest.builder()
                .description(description1)
                .priceInCents(priceInCents1)
                .build();

        String description2 = "TEST";
        Integer priceInCents2 = 100;

        TicketPricesRequest request2 = TicketPricesRequest.builder()
                .description(description2)
                .priceInCents(priceInCents2)
                .build();

        // When
        List<TicketPriceEntity> result = TicketPriceEntityBuilder.buildWithList(Lists.newArrayList(request1, request2), cinemaEntity);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(description1, result.get(0).getDescription());
        assertEquals(priceInCents1, result.get(0).getPriceInCents());
        assertEquals(cinemaEntity, result.get(0).getCinema());

        assertEquals(description2, result.get(1).getDescription());
        assertEquals(priceInCents2, result.get(1).getPriceInCents());
        assertEquals(cinemaEntity, result.get(1).getCinema());
    }
}