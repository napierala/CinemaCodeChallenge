package pl.napierala.cinemacodechallenge.controller;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.TicketPriceEntity;
import pl.napierala.cinemacodechallenge.extmodel.*;
import pl.napierala.cinemacodechallenge.repository.CinemaRepository;
import pl.napierala.cinemacodechallenge.repository.TicketPriceRepository;
import pl.napierala.cinemacodechallenge.util.IntegrationTest;
import pl.napierala.cinemacodechallenge.util.RequestUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Category(IntegrationTest.class)
public class CinemaControllerIntegrationTest {

    @Autowired
    private TicketPriceRepository ticketPriceRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldOverrideWithNewValuesIfThereIsOnlyOnePrice() throws Exception {

        // Given
        String cinemaCode = "TEST_CINEMA_CCIT_1";
        String cinemaName = "TEST_CINEMA_NAME";
        String cinemaAddress = "TEST ADDRESS";

        CinemaEntity cinemaEntity = cinemaRepository.save(
                CinemaEntity.builder()
                        .code(cinemaCode)
                        .name(cinemaName)
                        .address(cinemaAddress)
                        .build()
        );
        ticketPriceRepository.save(
                TicketPriceEntity.builder()
                        .cinema(cinemaEntity)
                        .description("BEFORE_UPDATE")
                        .priceInCents(100)
                        .build()
        );

        String descriptionAfterUpdate = "AFTER_UPDATE";
        Integer priceInCentsAfterUpdate = 200;

        UpdateTicketPricesRequest request = UpdateTicketPricesRequest.builder()
                .cinemaCode(cinemaCode)
                .ticketPrices(Lists.newArrayList(
                        TicketPricesRequest.builder()
                                .description(descriptionAfterUpdate)
                                .priceInCents(priceInCentsAfterUpdate)
                                .build()
                ))
                .build();

        // When
        UpdateTicketPricesResponse updateTicketPricesResponse = RequestUtil.request(restTemplate, "/cinema/updateTicketPrices", HttpMethod.POST, "admin", "admin_password", UpdateTicketPricesResponse.class, request);

        //Then
        assertNotNull(updateTicketPricesResponse);
        assertNotNull(updateTicketPricesResponse.getCinema());
        assertEquals(cinemaCode, updateTicketPricesResponse.getCinema().getCode());
        assertEquals(cinemaName, updateTicketPricesResponse.getCinema().getName());
        assertEquals(cinemaAddress, updateTicketPricesResponse.getCinema().getAddress());

        List<TicketPriceEntity> ticketPricesAfterUpdate = ticketPriceRepository.findByCinema(cinemaEntity);

        assertNotNull(ticketPricesAfterUpdate);
        assertEquals(1, ticketPricesAfterUpdate.size());
        assertEquals(cinemaEntity, ticketPricesAfterUpdate.get(0).getCinema());
        assertEquals(descriptionAfterUpdate, ticketPricesAfterUpdate.get(0).getDescription());
        assertEquals(priceInCentsAfterUpdate, ticketPricesAfterUpdate.get(0).getPriceInCents());
    }

    @Test
    public void shouldOverrideWithNewValuesIfThereAreTwoPrices() throws Exception {

        // Given
        String cinemaCode = "TEST_CINEMA_CCIT_2";
        String cinemaName = "TEST_CINEMA_2_NAME";
        String cinemaAddress = "TEST ADDRESS";

        CinemaEntity cinemaEntity = cinemaRepository.save(
                CinemaEntity.builder()
                        .code(cinemaCode)
                        .name(cinemaName)
                        .address(cinemaAddress)
                        .build()
        );
        ticketPriceRepository.save(
                TicketPriceEntity.builder()
                        .cinema(cinemaEntity)
                        .description("BEFORE_UPDATE")
                        .priceInCents(100)
                        .build()
        );

        ticketPriceRepository.save(
                TicketPriceEntity.builder()
                        .cinema(cinemaEntity)
                        .description("BEFORE_UPDATE_2")
                        .priceInCents(500)
                        .build()
        );

        String descriptionAfterUpdate = "AFTER_UPDATE";
        Integer priceInCentsAfterUpdate = 200;

        UpdateTicketPricesRequest request = UpdateTicketPricesRequest.builder()
                .cinemaCode(cinemaCode)
                .ticketPrices(Lists.newArrayList(
                        TicketPricesRequest.builder()
                                .description(descriptionAfterUpdate)
                                .priceInCents(priceInCentsAfterUpdate)
                                .build()
                ))
                .build();

        // When
        UpdateTicketPricesResponse updateTicketPricesResponse = RequestUtil.request(restTemplate, "/cinema/updateTicketPrices", HttpMethod.POST, "admin", "admin_password", UpdateTicketPricesResponse.class, request);

        //Then
        assertNotNull(updateTicketPricesResponse);
        assertNotNull(updateTicketPricesResponse.getCinema());
        assertEquals(cinemaCode, updateTicketPricesResponse.getCinema().getCode());
        assertEquals(cinemaName, updateTicketPricesResponse.getCinema().getName());
        assertEquals(cinemaAddress, updateTicketPricesResponse.getCinema().getAddress());

        List<TicketPriceEntity> ticketPricesAfterUpdate = ticketPriceRepository.findByCinema(cinemaEntity);

        assertNotNull(ticketPricesAfterUpdate);
        assertEquals(1, ticketPricesAfterUpdate.size());
        assertEquals(cinemaEntity, ticketPricesAfterUpdate.get(0).getCinema());
        assertEquals(descriptionAfterUpdate, ticketPricesAfterUpdate.get(0).getDescription());
        assertEquals(priceInCentsAfterUpdate, ticketPricesAfterUpdate.get(0).getPriceInCents());
    }

    @Test
    public void shouldReturnCorrectDetailsForCinema() throws Exception {

        // Given
        String cinemaCode = "TEST_CINEMA_CCIT_3";
        String cinemaName = "TEST_CINEMA_NAME";
        String cinemaAddress = "TEST ADDRESS";

        CinemaEntity cinemaEntity = cinemaRepository.save(
                CinemaEntity.builder()
                        .code(cinemaCode)
                        .name(cinemaName)
                        .address(cinemaAddress)
                        .build()
        );

        String description = "DESCRIPTION";
        Integer priceInCents = 100;

        ticketPriceRepository.save(
                TicketPriceEntity.builder()
                        .cinema(cinemaEntity)
                        .description(description)
                        .priceInCents(priceInCents)
                        .build()
        );

        CinemaDetailsRequest request = CinemaDetailsRequest.builder()
                .cinemaCode(cinemaCode)
                .build();

        // When
        CinemaDetailsResponse result = RequestUtil.request(restTemplate, "/cinema/details", HttpMethod.POST, null, null, CinemaDetailsResponse.class, request);

        //Then
        assertNotNull(result);
        assertEquals(cinemaCode, result.getCode());
        assertEquals(cinemaName, result.getName());
        assertEquals(cinemaAddress, result.getAddress());

        assertNotNull(result.getTicketPrices());
        assertEquals(1, result.getTicketPrices().size());
        assertEquals(description, result.getTicketPrices().get(0).getDescription());
        assertEquals(priceInCents, result.getTicketPrices().get(0).getPriceInCents());
    }
}