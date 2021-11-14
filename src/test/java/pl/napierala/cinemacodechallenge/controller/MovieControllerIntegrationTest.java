package pl.napierala.cinemacodechallenge.controller;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import pl.napierala.cinemacodechallenge.entity.MovieEntity;
import pl.napierala.cinemacodechallenge.entity.MovieRatingEntity;
import pl.napierala.cinemacodechallenge.entity.UserEntity;
import pl.napierala.cinemacodechallenge.extmodel.MovieDetailsRequest;
import pl.napierala.cinemacodechallenge.extmodel.MovieDetailsResponse;
import pl.napierala.cinemacodechallenge.extmodel.RateAMovieRequest;
import pl.napierala.cinemacodechallenge.extmodel.RateAMovieResponse;
import pl.napierala.cinemacodechallenge.repository.MovieRatingRepository;
import pl.napierala.cinemacodechallenge.repository.MovieRepository;
import pl.napierala.cinemacodechallenge.repository.UserRepository;
import pl.napierala.cinemacodechallenge.util.IntegrationTest;
import pl.napierala.cinemacodechallenge.util.RequestUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Category(IntegrationTest.class)
public class MovieControllerIntegrationTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieRatingRepository movieRatingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnTheFullDetailsAlongWithUserRating() throws Exception {

        // Given
        String movieCode = "F&F1_TEST_MCIT_1";
        String name = "The Fast and the Furious";
        String imdbCode = "tt0232500";

        MovieEntity movieEntity = movieRepository.save(
                MovieEntity.builder()
                        .code(movieCode)
                        .name(name)
                        .imdbId(imdbCode)
                        .build()
        );

        Integer movieRating = 2;

        movieRatingRepository.save(
                MovieRatingEntity.builder()
                        .movie(movieEntity)
                        .user(userRepository.findByUserName("user").get())
                        .rating(movieRating)
                        .build()
        );

        MovieDetailsRequest request = MovieDetailsRequest.builder()
                .movieCode(movieCode)
                .build();

        // When
        MovieDetailsResponse movieDetailsResponse = RequestUtil.request(restTemplate, "/movie/details", HttpMethod.POST, "user", "user_password", MovieDetailsResponse.class, request);

        //Then
        assertNotNull(movieDetailsResponse);
        assertNotNull(movieDetailsResponse.getCode());
        assertNotNull(movieDetailsResponse.getName());
        assertFalse(movieDetailsResponse.getName().isEmpty());
        assertEquals(movieRating, movieDetailsResponse.getUsersRating());
        assertNotNull(movieDetailsResponse.getImdbRating());
        assertEquals(LocalDate.of(2001, Month.JUNE, 22), movieDetailsResponse.getReleasedDate());
        assertEquals(new Integer(106), movieDetailsResponse.getRunTimeInMinutes());
    }

    @Test
    public void shouldReturnTheFullDetailsWithoutTheUserRating() throws Exception {

        // Given
        String movieCode = "F&F1_TEST_MCIT_2";
        String name = "The Fast and the Furious";
        String imdbCode = "tt0232500";

        MovieEntity movieEntity = movieRepository.save(
                MovieEntity.builder()
                        .code(movieCode)
                        .name(name)
                        .imdbId(imdbCode)
                        .build()
        );

        MovieDetailsRequest request = MovieDetailsRequest.builder()
                .movieCode(movieCode)
                .build();

        // When
        MovieDetailsResponse movieDetailsResponse = RequestUtil.request(restTemplate, "/movie/details", HttpMethod.POST, "user2", "user_password", MovieDetailsResponse.class, request);

        //Then
        assertNotNull(movieDetailsResponse);
        assertNotNull(movieDetailsResponse.getCode());
        assertNotNull(movieDetailsResponse.getName());
        assertFalse(movieDetailsResponse.getName().isEmpty());
        assertNull(movieDetailsResponse.getUsersRating());
        assertNotNull(movieDetailsResponse.getImdbRating());
        assertEquals(LocalDate.of(2001, Month.JUNE, 22), movieDetailsResponse.getReleasedDate());
        assertEquals(new Integer(106), movieDetailsResponse.getRunTimeInMinutes());
    }

    @Test
    public void shouldAddAMovieRatingIfNoneExistsBeforeHand() throws Exception {

        // Given
        String movieCode = "F&F1_TEST_MCIT_3";
        String name = "The Fast and the Furious";
        String imdbCode = "tt0232500";

        MovieEntity movieEntity = movieRepository.save(
                MovieEntity.builder()
                        .code(movieCode)
                        .name(name)
                        .imdbId(imdbCode)
                        .build()
        );

        Integer movieRating = 5;

        RateAMovieRequest request = RateAMovieRequest.builder()
                .movieCode(movieCode)
                .rating(movieRating)
                .build();

        UserEntity user3 = userRepository.findByUserName("user3").get();

        // When
        RateAMovieResponse rateAMovieResponse = RequestUtil.request(restTemplate, "/movie/rate", HttpMethod.POST, "user3", "user_password", RateAMovieResponse.class, request);

        //Then
        assertNotNull(rateAMovieResponse);
        assertTrue(rateAMovieResponse.isSuccessful());

        Optional<MovieRatingEntity> movieRatingEntity = movieRatingRepository.findByMovieAndUser(movieEntity, user3);

        assertTrue(movieRatingEntity.isPresent());
        assertEquals(movieEntity, movieRatingEntity.get().getMovie());
        assertEquals(movieRating, movieRatingEntity.get().getRating());
        assertEquals(user3, movieRatingEntity.get().getUser());
    }
}