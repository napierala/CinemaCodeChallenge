package pl.napierala.cinemacodechallenge.builder;

import org.junit.Test;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.MovieEntity;
import pl.napierala.cinemacodechallenge.entity.MovieShowingEntity;
import pl.napierala.cinemacodechallenge.extmodel.AddMovieShowingRequest;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MovieShowingEntityBuilderTest {

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

        String movieCode = "CODE";
        String movieName = "NAME";

        MovieEntity movieEntity = MovieEntity.builder()
                .code(movieCode)
                .name(movieName)
                .imdbId("ID")
                .build();

        LocalDateTime dateTime1 = LocalDateTime.of(2004, 11, 26, 8, 0);
        String room1 = "1A";

        AddMovieShowingRequest request = AddMovieShowingRequest.builder()
                .cinemaCode(cinemaEntity.getCode())
                .movieCode(movieEntity.getCode())
                .dateTime(dateTime1)
                .room(room1)
                .build();

        // When
        MovieShowingEntity result = MovieShowingEntityBuilder.buildWith(cinemaEntity, movieEntity, request);

        // Then
        assertNotNull(result);

        assertNotNull(result.getCinema());
        assertEquals(cinemaEntity.getCode(), result.getCinema().getCode());
        assertEquals(cinemaEntity.getName(), result.getCinema().getName());
        assertEquals(cinemaEntity.getAddress(), result.getCinema().getAddress());

        assertNotNull(result.getMovie());
        assertEquals(movieEntity.getCode(), result.getMovie().getCode());
        assertEquals(movieEntity.getName(), result.getMovie().getName());

        assertNotNull(result.getUuid());
        assertEquals(dateTime1, result.getDateTime());
        assertEquals(room1, result.getRoom());
    }
}