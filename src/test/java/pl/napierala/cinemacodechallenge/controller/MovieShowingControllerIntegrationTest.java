package pl.napierala.cinemacodechallenge.controller;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.MovieEntity;
import pl.napierala.cinemacodechallenge.entity.MovieShowingEntity;
import pl.napierala.cinemacodechallenge.extmodel.*;
import pl.napierala.cinemacodechallenge.repository.CinemaRepository;
import pl.napierala.cinemacodechallenge.repository.MovieRepository;
import pl.napierala.cinemacodechallenge.repository.MovieShowingRepository;
import pl.napierala.cinemacodechallenge.util.IntegrationTest;
import pl.napierala.cinemacodechallenge.util.RequestUtil;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Category(IntegrationTest.class)
public class MovieShowingControllerIntegrationTest {

    @Autowired
    private MovieShowingRepository movieShowingRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldCorrectlyAddAMovieShowing() throws Exception {

        // Given

        String cinemaCode = "TEST_CINEMA_MSCIT_1";
        String cinemaName = "TEST_CINEMA_NAME";
        String cinemaAddress = "TEST ADDRESS";

        CinemaEntity cinemaEntity = cinemaRepository.save(
                CinemaEntity.builder()
                        .code(cinemaCode)
                        .name(cinemaName)
                        .address(cinemaAddress)
                        .build()
        );

        String movieCode = "F&F1_TEST_MSCIT_1";
        String movieName = "The Fast and the Furious";
        String imdbCode = "tt0232500";

        MovieEntity movieEntity = movieRepository.save(
                MovieEntity.builder()
                        .code(movieCode)
                        .name(movieName)
                        .imdbId(imdbCode)
                        .build()
        );

        LocalDateTime dateTime = LocalDateTime.of(2004, 11, 26, 8, 0);
        String room = "26";

        AddMovieShowingRequest request = AddMovieShowingRequest.builder()
                .cinemaCode(cinemaCode)
                .movieCode(movieCode)
                .dateTime(dateTime)
                .room(room)
                .build();

        // When
        MovieShowingResponse result = RequestUtil.request(restTemplate, "/movieShowing/add", HttpMethod.POST, "admin", "admin_password", MovieShowingResponse.class, request);

        //Then
        assertMovieShowingResponse(result, cinemaCode, cinemaName, cinemaAddress, movieCode, movieName, dateTime, room);

        Optional<MovieShowingEntity> movieShowingEntityOptional = movieShowingRepository.findByUuid(result.getUuid());
        assertTrue(movieShowingEntityOptional.isPresent());

        MovieShowingEntity movieShowingEntity = movieShowingEntityOptional.get();

        assertMovieShowingEntity(movieShowingEntity, cinemaCode, cinemaName, cinemaAddress, movieCode, movieName, dateTime, room);
    }

    @Test
    public void shouldCorrectlyDeleteAMovieShowing() throws Exception {

        // Given

        String cinemaCode = "TEST_CINEMA_MSCIT_2";
        String cinemaName = "TEST_CINEMA_NAME";
        String cinemaAddress = "TEST ADDRESS";

        CinemaEntity cinemaEntity = cinemaRepository.save(
                CinemaEntity.builder()
                        .code(cinemaCode)
                        .name(cinemaName)
                        .address(cinemaAddress)
                        .build()
        );

        String movieCode = "F&F1_TEST_MSCIT_2";
        String movieName = "The Fast and the Furious";
        String imdbCode = "tt0232500";

        MovieEntity movieEntity = movieRepository.save(
                MovieEntity.builder()
                        .code(movieCode)
                        .name(movieName)
                        .imdbId(imdbCode)
                        .build()
        );

        LocalDateTime dateTime = LocalDateTime.of(2004, 11, 26, 8, 0);
        String room = "26";
        String uuid = UUID.randomUUID().toString();

        MovieShowingEntity movieShowingEntity = movieShowingRepository.save(
                MovieShowingEntity.builder()
                        .cinema(cinemaEntity)
                        .movie(movieEntity)
                        .uuid(uuid)
                        .dateTime(dateTime)
                        .room(room)
                        .build()
        );

        DeleteMovieShowingRequest request = DeleteMovieShowingRequest.builder()
                .uuid(uuid)
                .build();

        // When
        MovieShowingResponse result = RequestUtil.request(restTemplate, "/movieShowing/delete", HttpMethod.DELETE, "admin", "admin_password", MovieShowingResponse.class, request);

        //Then
        assertMovieShowingResponse(result, cinemaCode, cinemaName, cinemaAddress, movieCode, movieName, dateTime, room);

        Optional<MovieShowingEntity> movieShowingEntityOptional = movieShowingRepository.findByUuid(result.getUuid());
        assertFalse(movieShowingEntityOptional.isPresent());
    }

    @Test
    public void shouldCorrectlyUpdateAMovieShowingDateTimeAndRoom() throws Exception {

        // Given

        String cinemaCode = "TEST_CINEMA_MSCIT_3";
        String cinemaName = "TEST_CINEMA_NAME";
        String cinemaAddress = "TEST ADDRESS";

        CinemaEntity cinemaEntity = cinemaRepository.save(
                CinemaEntity.builder()
                        .code(cinemaCode)
                        .name(cinemaName)
                        .address(cinemaAddress)
                        .build()
        );

        String movieCode = "F&F1_TEST_MSCIT_3";
        String movieName = "The Fast and the Furious";
        String imdbCode = "tt0232500";

        MovieEntity movieEntity = movieRepository.save(
                MovieEntity.builder()
                        .code(movieCode)
                        .name(movieName)
                        .imdbId(imdbCode)
                        .build()
        );

        LocalDateTime dateTime = LocalDateTime.of(2004, 11, 26, 8, 0);
        String room = "26";
        String uuid = UUID.randomUUID().toString();

        MovieShowingEntity movieShowingEntity = movieShowingRepository.save(
                MovieShowingEntity.builder()
                        .cinema(cinemaEntity)
                        .movie(movieEntity)
                        .uuid(uuid)
                        .dateTime(dateTime)
                        .room(room)
                        .build()
        );

        LocalDateTime newDateTime = LocalDateTime.of(2004, 11, 26, 8, 0);
        String newRoom = "50";

        UpdateMovieShowingRequest request = UpdateMovieShowingRequest.builder()
                .uuid(uuid)
                .dateTime(newDateTime)
                .room(newRoom)
                .build();

        // When
        MovieShowingResponse result = RequestUtil.request(restTemplate, "/movieShowing/update", HttpMethod.PUT, "admin", "admin_password", MovieShowingResponse.class, request);

        //Then
        assertMovieShowingResponse(result, cinemaCode, cinemaName, cinemaAddress, movieCode, movieName, newDateTime, newRoom);

        Optional<MovieShowingEntity> movieShowingEntityOptional = movieShowingRepository.findByUuid(result.getUuid());
        assertTrue(movieShowingEntityOptional.isPresent());

        MovieShowingEntity movieShowingEntityToCheck = movieShowingEntityOptional.get();

        assertMovieShowingEntity(movieShowingEntityToCheck, cinemaCode, cinemaName, cinemaAddress, movieCode, movieName, newDateTime, newRoom);
    }

    @Test
    public void shouldCorrectlyUpdateAMovieShowingDateTime() throws Exception {

        // Given

        String cinemaCode = "TEST_CINEMA_MSCIT_4";
        String cinemaName = "TEST_CINEMA_NAME";
        String cinemaAddress = "TEST ADDRESS";

        CinemaEntity cinemaEntity = cinemaRepository.save(
                CinemaEntity.builder()
                        .code(cinemaCode)
                        .name(cinemaName)
                        .address(cinemaAddress)
                        .build()
        );

        String movieCode = "F&F1_TEST_MSCIT_4";
        String movieName = "The Fast and the Furious";
        String imdbCode = "tt0232500";

        MovieEntity movieEntity = movieRepository.save(
                MovieEntity.builder()
                        .code(movieCode)
                        .name(movieName)
                        .imdbId(imdbCode)
                        .build()
        );

        LocalDateTime dateTime = LocalDateTime.of(2004, 11, 26, 8, 0);
        String room = "26";
        String uuid = UUID.randomUUID().toString();

        MovieShowingEntity movieShowingEntity = movieShowingRepository.save(
                MovieShowingEntity.builder()
                        .cinema(cinemaEntity)
                        .movie(movieEntity)
                        .uuid(uuid)
                        .dateTime(dateTime)
                        .room(room)
                        .build()
        );

        LocalDateTime newDateTime = LocalDateTime.of(2004, 11, 26, 8, 0);

        UpdateMovieShowingRequest request = UpdateMovieShowingRequest.builder()
                .uuid(uuid)
                .dateTime(newDateTime)
                .build();

        // When
        MovieShowingResponse result = RequestUtil.request(restTemplate, "/movieShowing/update", HttpMethod.PUT, "admin", "admin_password", MovieShowingResponse.class, request);

        //Then
        assertMovieShowingResponse(result, cinemaCode, cinemaName, cinemaAddress, movieCode, movieName, newDateTime, room);

        Optional<MovieShowingEntity> movieShowingEntityOptional = movieShowingRepository.findByUuid(result.getUuid());
        assertTrue(movieShowingEntityOptional.isPresent());

        MovieShowingEntity movieShowingEntityToCheck = movieShowingEntityOptional.get();

        assertMovieShowingEntity(movieShowingEntityToCheck, cinemaCode, cinemaName, cinemaAddress, movieCode, movieName, newDateTime, room);
    }

    @Test
    public void shouldCorrectlyUpdateAMovieShowingRoom() throws Exception {

        // Given

        String cinemaCode = "TEST_CINEMA_MSCIT_5";
        String cinemaName = "TEST_CINEMA_NAME";
        String cinemaAddress = "TEST ADDRESS";

        CinemaEntity cinemaEntity = cinemaRepository.save(
                CinemaEntity.builder()
                        .code(cinemaCode)
                        .name(cinemaName)
                        .address(cinemaAddress)
                        .build()
        );

        String movieCode = "F&F1_TEST_MSCIT_5";
        String movieName = "The Fast and the Furious";
        String imdbCode = "tt0232500";

        MovieEntity movieEntity = movieRepository.save(
                MovieEntity.builder()
                        .code(movieCode)
                        .name(movieName)
                        .imdbId(imdbCode)
                        .build()
        );

        LocalDateTime dateTime = LocalDateTime.of(2004, 11, 26, 8, 0);
        String room = "26";
        String uuid = UUID.randomUUID().toString();

        MovieShowingEntity movieShowingEntity = movieShowingRepository.save(
                MovieShowingEntity.builder()
                        .cinema(cinemaEntity)
                        .movie(movieEntity)
                        .uuid(uuid)
                        .dateTime(dateTime)
                        .room(room)
                        .build()
        );

        String newRoom = "100";

        UpdateMovieShowingRequest request = UpdateMovieShowingRequest.builder()
                .uuid(uuid)
                .room(newRoom)
                .build();

        // When
        MovieShowingResponse result = RequestUtil.request(restTemplate, "/movieShowing/update", HttpMethod.PUT, "admin", "admin_password", MovieShowingResponse.class, request);

        //Then
        assertMovieShowingResponse(result, cinemaCode, cinemaName, cinemaAddress, movieCode, movieName, dateTime, newRoom);

        Optional<MovieShowingEntity> movieShowingEntityOptional = movieShowingRepository.findByUuid(result.getUuid());
        assertTrue(movieShowingEntityOptional.isPresent());

        MovieShowingEntity movieShowingEntityToCheck = movieShowingEntityOptional.get();

        assertMovieShowingEntity(movieShowingEntityToCheck, cinemaCode, cinemaName, cinemaAddress, movieCode, movieName, dateTime, newRoom);
    }

    @Test
    public void shouldCorrectlyUpdateAMovieShowingWhenNothingIsSpecified() throws Exception {

        // Given

        String cinemaCode = "TEST_CINEMA_MSCIT_6";
        String cinemaName = "TEST_CINEMA_NAME";
        String cinemaAddress = "TEST ADDRESS";

        CinemaEntity cinemaEntity = cinemaRepository.save(
                CinemaEntity.builder()
                        .code(cinemaCode)
                        .name(cinemaName)
                        .address(cinemaAddress)
                        .build()
        );

        String movieCode = "F&F1_TEST_MSCIT_6";
        String movieName = "The Fast and the Furious";
        String imdbCode = "tt0232500";

        MovieEntity movieEntity = movieRepository.save(
                MovieEntity.builder()
                        .code(movieCode)
                        .name(movieName)
                        .imdbId(imdbCode)
                        .build()
        );

        LocalDateTime dateTime = LocalDateTime.of(2004, 11, 26, 8, 0);
        String room = "26";
        String uuid = UUID.randomUUID().toString();

        MovieShowingEntity movieShowingEntity = movieShowingRepository.save(
                MovieShowingEntity.builder()
                        .cinema(cinemaEntity)
                        .movie(movieEntity)
                        .uuid(uuid)
                        .dateTime(dateTime)
                        .room(room)
                        .build()
        );

        UpdateMovieShowingRequest request = UpdateMovieShowingRequest.builder()
                .uuid(uuid)
                .build();

        // When
        MovieShowingResponse result = RequestUtil.request(restTemplate, "/movieShowing/update", HttpMethod.PUT, "admin", "admin_password", MovieShowingResponse.class, request);

        //Then
        assertMovieShowingResponse(result, cinemaCode, cinemaName, cinemaAddress, movieCode, movieName, dateTime, room);

        Optional<MovieShowingEntity> movieShowingEntityOptional = movieShowingRepository.findByUuid(result.getUuid());
        assertTrue(movieShowingEntityOptional.isPresent());

        MovieShowingEntity movieShowingEntityToCheck = movieShowingEntityOptional.get();

        assertMovieShowingEntity(movieShowingEntityToCheck, cinemaCode, cinemaName, cinemaAddress, movieCode, movieName, dateTime, room);
    }

    @Test
    public void shouldCorrectlyFindTwoMovieShowingsWhenThereAreOnlyTwoShowings() throws Exception {

        // Given

        String cinemaCode = "TEST_CINEMA_MSCIT_7";
        String cinemaName = "TEST_CINEMA_NAME";
        String cinemaAddress = "TEST ADDRESS";

        CinemaEntity cinemaEntity = cinemaRepository.save(
                CinemaEntity.builder()
                        .code(cinemaCode)
                        .name(cinemaName)
                        .address(cinemaAddress)
                        .build()
        );

        String movieCode = "F&F1_TEST_MSCIT_7";
        String movieName = "The Fast and the Furious";
        String imdbCode = "tt0232500";

        MovieEntity movieEntity = movieRepository.save(
                MovieEntity.builder()
                        .code(movieCode)
                        .name(movieName)
                        .imdbId(imdbCode)
                        .build()
        );

        LocalDateTime dateTime1 = LocalDateTime.of(2004, 11, 26, 8, 0);
        String room1 = "26";
        String uuid1 = UUID.randomUUID().toString();

        MovieShowingEntity movieShowingEntity1 = movieShowingRepository.save(
                MovieShowingEntity.builder()
                        .cinema(cinemaEntity)
                        .movie(movieEntity)
                        .uuid(uuid1)
                        .dateTime(dateTime1)
                        .room(room1)
                        .build()
        );

        LocalDateTime dateTime2 = LocalDateTime.of(2004, 11, 26, 15, 0);
        String room2 = "50";
        String uuid2 = UUID.randomUUID().toString();

        MovieShowingEntity movieShowingEntity2 = movieShowingRepository.save(
                MovieShowingEntity.builder()
                        .cinema(cinemaEntity)
                        .movie(movieEntity)
                        .uuid(uuid2)
                        .dateTime(dateTime2)
                        .room(room2)
                        .build()
        );

        FindMovieShowingsRequest request = FindMovieShowingsRequest.builder()
                .cinemaCode(cinemaCode)
                .fromDateTime(LocalDateTime.of(2004, 11, 26, 0, 0, 0))
                .toDateTime(LocalDateTime.of(2004, 11, 26, 23, 59, 59))
                .build();

        // When
        FindMovieShowingsResponse result = RequestUtil.request(restTemplate, "/movieShowing/find", HttpMethod.POST, null, null, FindMovieShowingsResponse.class, request);

        //Then
        assertNotNull(result);
        assertNotNull(result.getMovieShowings());
        assertEquals(2, result.getMovieShowings().size());

        assertMovieShowingResponse(result.getMovieShowings().get(0), cinemaCode, cinemaName, cinemaAddress, movieCode, movieName, dateTime1, room1);
        assertMovieShowingResponse(result.getMovieShowings().get(1), cinemaCode, cinemaName, cinemaAddress, movieCode, movieName, dateTime2, room2);
    }

    @Test
    public void shouldCorrectlyFindOneMovieShowingsWhenThereOnlyAreTwoShowings() throws Exception {

        // Given

        String cinemaCode = "TEST_CINEMA_MSCIT_8";
        String cinemaName = "TEST_CINEMA_NAME";
        String cinemaAddress = "TEST ADDRESS";

        CinemaEntity cinemaEntity = cinemaRepository.save(
                CinemaEntity.builder()
                        .code(cinemaCode)
                        .name(cinemaName)
                        .address(cinemaAddress)
                        .build()
        );

        String movieCode = "F&F1_TEST_MSCIT_8";
        String movieName = "The Fast and the Furious";
        String imdbCode = "tt0232500";

        MovieEntity movieEntity = movieRepository.save(
                MovieEntity.builder()
                        .code(movieCode)
                        .name(movieName)
                        .imdbId(imdbCode)
                        .build()
        );

        LocalDateTime dateTime1 = LocalDateTime.of(2004, 11, 26, 8, 0);
        String room1 = "26";
        String uuid1 = UUID.randomUUID().toString();

        MovieShowingEntity movieShowingEntity1 = movieShowingRepository.save(
                MovieShowingEntity.builder()
                        .cinema(cinemaEntity)
                        .movie(movieEntity)
                        .uuid(uuid1)
                        .dateTime(dateTime1)
                        .room(room1)
                        .build()
        );

        LocalDateTime dateTime2 = LocalDateTime.of(2004, 11, 26, 15, 0);
        String room2 = "50";
        String uuid2 = UUID.randomUUID().toString();

        MovieShowingEntity movieShowingEntity2 = movieShowingRepository.save(
                MovieShowingEntity.builder()
                        .cinema(cinemaEntity)
                        .movie(movieEntity)
                        .uuid(uuid2)
                        .dateTime(dateTime2)
                        .room(room2)
                        .build()
        );

        FindMovieShowingsRequest request = FindMovieShowingsRequest.builder()
                .cinemaCode(cinemaCode)
                .fromDateTime(LocalDateTime.of(2004, 11, 26, 0, 0, 0))
                .toDateTime(LocalDateTime.of(2004, 11, 26, 12, 59, 59))
                .build();

        // When
        FindMovieShowingsResponse result = RequestUtil.request(restTemplate, "/movieShowing/find", HttpMethod.POST, null, null, FindMovieShowingsResponse.class, request);

        //Then
        assertNotNull(result);
        assertNotNull(result.getMovieShowings());
        assertEquals(1, result.getMovieShowings().size());

        assertMovieShowingResponse(result.getMovieShowings().get(0), cinemaCode, cinemaName, cinemaAddress, movieCode, movieName, dateTime1, room1);
    }

    @Test
    public void shouldCorrectlyFindZeroMovieShowingsWhenThereOnlyAreTwoShowings() throws Exception {

        // Given

        String cinemaCode = "TEST_CINEMA_MSCIT_9";
        String cinemaName = "TEST_CINEMA_NAME";
        String cinemaAddress = "TEST ADDRESS";

        CinemaEntity cinemaEntity = cinemaRepository.save(
                CinemaEntity.builder()
                        .code(cinemaCode)
                        .name(cinemaName)
                        .address(cinemaAddress)
                        .build()
        );

        String movieCode = "F&F1_TEST_MSCIT_9";
        String movieName = "The Fast and the Furious";
        String imdbCode = "tt0232500";

        MovieEntity movieEntity = movieRepository.save(
                MovieEntity.builder()
                        .code(movieCode)
                        .name(movieName)
                        .imdbId(imdbCode)
                        .build()
        );

        LocalDateTime dateTime1 = LocalDateTime.of(2004, 11, 26, 8, 0);
        String room1 = "26";
        String uuid1 = UUID.randomUUID().toString();

        MovieShowingEntity movieShowingEntity1 = movieShowingRepository.save(
                MovieShowingEntity.builder()
                        .cinema(cinemaEntity)
                        .movie(movieEntity)
                        .uuid(uuid1)
                        .dateTime(dateTime1)
                        .room(room1)
                        .build()
        );

        LocalDateTime dateTime2 = LocalDateTime.of(2004, 11, 26, 15, 0);
        String room2 = "50";
        String uuid2 = UUID.randomUUID().toString();

        MovieShowingEntity movieShowingEntity2 = movieShowingRepository.save(
                MovieShowingEntity.builder()
                        .cinema(cinemaEntity)
                        .movie(movieEntity)
                        .uuid(uuid2)
                        .dateTime(dateTime2)
                        .room(room2)
                        .build()
        );

        FindMovieShowingsRequest request = FindMovieShowingsRequest.builder()
                .cinemaCode(cinemaCode)
                .fromDateTime(LocalDateTime.of(2004, 11, 27, 0, 0, 0))
                .toDateTime(LocalDateTime.of(2004, 11, 27, 12, 59, 59))
                .build();

        // When
        FindMovieShowingsResponse result = RequestUtil.request(restTemplate, "/movieShowing/find", HttpMethod.POST, null, null, FindMovieShowingsResponse.class, request);

        //Then
        assertNotNull(result);
        assertNotNull(result.getMovieShowings());
        assertEquals(0, result.getMovieShowings().size());
    }

    private void assertMovieShowingResponse(MovieShowingResponse toCheck, String cinemaCode, String cinemaName,
                                            String cinemaAddress, String movieCode, String movieName,
                                            LocalDateTime dateTime, String room) {

        assertNotNull(toCheck);

        assertNotNull(toCheck.getCinema());
        assertEquals(cinemaCode, toCheck.getCinema().getCode());
        assertEquals(cinemaName, toCheck.getCinema().getName());
        assertEquals(cinemaAddress, toCheck.getCinema().getAddress());

        assertNotNull(toCheck.getMovie());
        assertEquals(movieCode, toCheck.getMovie().getCode());
        assertEquals(movieName, toCheck.getMovie().getName());

        assertNotNull(toCheck.getUuid());
        assertTrue(toCheck.getUuid().length() > 10);

        assertEquals(dateTime, toCheck.getDateTime());
        assertEquals(room, toCheck.getRoom());
    }

    private void assertMovieShowingEntity(MovieShowingEntity toCheck, String cinemaCode, String cinemaName,
                                          String cinemaAddress, String movieCode, String movieName,
                                          LocalDateTime dateTime, String room) {

        assertNotNull(toCheck);

        assertNotNull(toCheck.getCinema());
        assertEquals(cinemaCode, toCheck.getCinema().getCode());
        assertEquals(cinemaName, toCheck.getCinema().getName());
        assertEquals(cinemaAddress, toCheck.getCinema().getAddress());

        assertNotNull(toCheck.getMovie());
        assertEquals(movieCode, toCheck.getMovie().getCode());
        assertEquals(movieName, toCheck.getMovie().getName());

        assertNotNull(toCheck.getUuid());
        assertTrue(toCheck.getUuid().length() > 10);

        assertEquals(dateTime, toCheck.getDateTime());
        assertEquals(room, toCheck.getRoom());
    }
}