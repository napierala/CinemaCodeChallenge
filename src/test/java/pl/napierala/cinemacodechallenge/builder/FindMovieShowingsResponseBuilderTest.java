package pl.napierala.cinemacodechallenge.builder;

import com.google.common.collect.Lists;
import org.junit.Test;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.MovieEntity;
import pl.napierala.cinemacodechallenge.entity.MovieShowingEntity;
import pl.napierala.cinemacodechallenge.extmodel.FindMovieShowingsResponse;
import pl.napierala.cinemacodechallenge.extmodel.MovieShowingResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FindMovieShowingsResponseBuilderTest {

    @Test
    public void shouldBuildCorrectlyForNull() {

        // Given

        // When
        FindMovieShowingsResponse result = FindMovieShowingsResponseBuilder.buildWithList(null);

        // Then
        assertNull(result);
    }

    @Test
    public void shouldBuildCorrectlyForEmpty() {

        // Given

        // When
        FindMovieShowingsResponse result = FindMovieShowingsResponseBuilder.buildWithList(new ArrayList<>());

        // Then
        assertNotNull(result);
        assertNotNull(result.getMovieShowings());
        assertEquals(0, result.getMovieShowings().size());
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

        String movieCode = "CODE";
        String movieName = "NAME";

        MovieEntity movieEntity = MovieEntity.builder()
                .code(movieCode)
                .name(movieName)
                .imdbId("ID")
                .build();

        String uuid1 = "#1";
        LocalDateTime dateTime1 = LocalDateTime.of(2004, 11, 26, 8, 0);
        String room1 = "1A";

        MovieShowingEntity movieShowing1 = MovieShowingEntity.builder()
                .cinema(cinemaEntity)
                .movie(movieEntity)
                .uuid(uuid1)
                .dateTime(dateTime1)
                .room(room1)
                .build();

        // When
        FindMovieShowingsResponse result = FindMovieShowingsResponseBuilder.buildWithList(Lists.newArrayList(movieShowing1));

        // Then
        assertNotNull(result);

        List<MovieShowingResponse> ms = result.getMovieShowings();

        assertNotNull(ms);
        assertEquals(1, ms.size());

        assertNotNull(ms.get(0).getCinema());
        assertEquals(cinemaEntity.getCode(), ms.get(0).getCinema().getCode());
        assertEquals(cinemaEntity.getName(), ms.get(0).getCinema().getName());
        assertEquals(cinemaEntity.getAddress(), ms.get(0).getCinema().getAddress());

        assertNotNull(ms.get(0).getMovie());
        assertEquals(movieEntity.getCode(), ms.get(0).getMovie().getCode());
        assertEquals(movieEntity.getName(), ms.get(0).getMovie().getName());

        assertEquals(uuid1, ms.get(0).getUuid());
        assertEquals(dateTime1, ms.get(0).getDateTime());
        assertEquals(room1, ms.get(0).getRoom());
    }

    @Test
    public void shouldBuildCorrectlyForTwoElements() {

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

        String uuid1 = "#1";
        LocalDateTime dateTime1 = LocalDateTime.of(2004, 11, 26, 8, 0);
        String room1 = "1A";

        MovieShowingEntity movieShowing1 = MovieShowingEntity.builder()
                .cinema(cinemaEntity)
                .movie(movieEntity)
                .uuid(uuid1)
                .dateTime(dateTime1)
                .room(room1)
                .build();

        String uuid2 = "#2";
        LocalDateTime dateTime2 = LocalDateTime.of(2004, 11, 27, 8, 0);
        String room2 = "1B";

        MovieShowingEntity movieShowing2 = MovieShowingEntity.builder()
                .cinema(cinemaEntity)
                .movie(movieEntity)
                .uuid(uuid2)
                .dateTime(dateTime2)
                .room(room2)
                .build();

        // When
        FindMovieShowingsResponse result = FindMovieShowingsResponseBuilder.buildWithList(Lists.newArrayList(movieShowing1, movieShowing2));

        // Then
        assertNotNull(result);

        List<MovieShowingResponse> ms = result.getMovieShowings();

        assertNotNull(ms);
        assertEquals(2, ms.size());

        // The first

        assertNotNull(ms.get(0).getCinema());
        assertEquals(cinemaEntity.getCode(), ms.get(0).getCinema().getCode());
        assertEquals(cinemaEntity.getName(), ms.get(0).getCinema().getName());
        assertEquals(cinemaEntity.getAddress(), ms.get(0).getCinema().getAddress());

        assertNotNull(ms.get(0).getMovie());
        assertEquals(movieEntity.getCode(), ms.get(0).getMovie().getCode());
        assertEquals(movieEntity.getName(), ms.get(0).getMovie().getName());

        assertEquals(uuid1, ms.get(0).getUuid());
        assertEquals(dateTime1, ms.get(0).getDateTime());
        assertEquals(room1, ms.get(0).getRoom());

        // The second

        assertNotNull(ms.get(1).getCinema());
        assertEquals(cinemaEntity.getCode(), ms.get(1).getCinema().getCode());
        assertEquals(cinemaEntity.getName(), ms.get(1).getCinema().getName());
        assertEquals(cinemaEntity.getAddress(), ms.get(1).getCinema().getAddress());

        assertNotNull(ms.get(1).getMovie());
        assertEquals(movieEntity.getCode(), ms.get(1).getMovie().getCode());
        assertEquals(movieEntity.getName(), ms.get(1).getMovie().getName());

        assertEquals(uuid2, ms.get(1).getUuid());
        assertEquals(dateTime2, ms.get(1).getDateTime());
        assertEquals(room2, ms.get(1).getRoom());
    }
}