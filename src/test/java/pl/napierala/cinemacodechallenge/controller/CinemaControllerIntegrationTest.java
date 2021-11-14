package pl.napierala.cinemacodechallenge.controller;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.TicketPriceEntity;
import pl.napierala.cinemacodechallenge.extmodel.TicketPricesRequest;
import pl.napierala.cinemacodechallenge.extmodel.UpdateTicketPricesRequest;
import pl.napierala.cinemacodechallenge.extmodel.UpdateTicketPricesResponse;
import pl.napierala.cinemacodechallenge.repository.CinemaRepository;
import pl.napierala.cinemacodechallenge.repository.TicketPriceRepository;
import pl.napierala.cinemacodechallenge.util.IntegrationTest;

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
        String cinemaCode = "TEST_CINEMA";
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
        UpdateTicketPricesResponse updateTicketPricesResponse = request("/cinema/updateTicketPrices", HttpMethod.POST, "admin", "admin_password", UpdateTicketPricesResponse.class, request);

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
        String cinemaCode = "TEST_CINEMA_2";
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
        UpdateTicketPricesResponse updateTicketPricesResponse = request("/cinema/updateTicketPrices", HttpMethod.POST, "admin", "admin_password", UpdateTicketPricesResponse.class, request);

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

    private <T> T request(String url, HttpMethod httpMethod, String user, String password, Class<T> responseClazz, Object request) throws Exception {

        // Given
        TestRestTemplate finalRestTemplate = this.restTemplate;

        if (user != null && password != null) {
            finalRestTemplate = finalRestTemplate.withBasicAuth(user, password);
        }

        return finalRestTemplate.exchange(url, httpMethod, new HttpEntity<>(request), responseClazz).getBody();
    }
}