package pl.napierala.cinemacodechallenge.builder;

import com.google.common.collect.Sets;
import org.junit.Test;
import pl.napierala.cinemacodechallenge.entity.MovieEntity;
import pl.napierala.cinemacodechallenge.entity.MovieRatingEntity;
import pl.napierala.cinemacodechallenge.entity.UserEntity;
import pl.napierala.cinemacodechallenge.extmodel.MovieDetailsResponse;
import pl.napierala.cinemacodechallenge.imdb.IMDBMovie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.junit.Assert.*;

public class MovieDetailsResponseBuilderTest {

    @Test
    public void shouldBuildCorrectlyWithUsersRating() {

        // Given
        String movieCode = "CODE";
        String movieName = "NAME";

        MovieEntity movieEntity = MovieEntity.builder()
                .code(movieCode)
                .name(movieName)
                .imdbId("ID")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .userName("test")
                .password("test_password")
                .createTime(LocalDateTime.now())
                .roles(Sets.newHashSet(UserEntity.REGULAR_USER_ROLE))
                .build();

        Integer movieRating = 5;

        MovieRatingEntity movieRatingEntity = MovieRatingEntity.builder()
                .movie(movieEntity)
                .user(userEntity)
                .rating(movieRating)
                .build();

        String title = "TEST_TITLE";
        LocalDate released = LocalDate.of(1992, Month.DECEMBER, 15);
        Double imdbRating = 2.0;
        Integer runtimeInMinutes = 100;

        IMDBMovie imdbMovie = IMDBMovie.builder()
                .title(title)
                .released(released)
                .rating(imdbRating)
                .runtimeInMinutes(runtimeInMinutes)
                .build();

        // When
        MovieDetailsResponse result = MovieDetailsResponseBuilder.buildWith(movieEntity, Optional.of(movieRatingEntity), imdbMovie);

        // Then
        assertNotNull(result);
        assertEquals(movieCode, result.getCode());
        assertEquals(title, result.getName());
        assertEquals(movieRating, result.getUsersRating());
        assertEquals(imdbRating, result.getImdbRating());
        assertEquals(released, result.getReleasedDate());
        assertEquals(runtimeInMinutes, result.getRunTimeInMinutes());
    }

    @Test
    public void shouldBuildCorrectlyWithoutUsersRating() {

        // Given
        String movieCode = "CODE";
        String movieName = "NAME";

        MovieEntity movieEntity = MovieEntity.builder()
                .code(movieCode)
                .name(movieName)
                .imdbId("ID")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .userName("test")
                .password("test_password")
                .createTime(LocalDateTime.now())
                .roles(Sets.newHashSet(UserEntity.REGULAR_USER_ROLE))
                .build();

        String title = "TEST_TITLE";
        LocalDate released = LocalDate.of(1992, Month.DECEMBER, 15);
        Double imdbRating = 2.0;
        Integer runtimeInMinutes = 100;

        IMDBMovie imdbMovie = IMDBMovie.builder()
                .title(title)
                .released(released)
                .rating(imdbRating)
                .runtimeInMinutes(runtimeInMinutes)
                .build();

        // When
        MovieDetailsResponse result = MovieDetailsResponseBuilder.buildWith(movieEntity, Optional.empty(), imdbMovie);

        // Then
        assertNotNull(result);
        assertEquals(movieCode, result.getCode());
        assertEquals(title, result.getName());
        assertNull(result.getUsersRating());
        assertEquals(imdbRating, result.getImdbRating());
        assertEquals(released, result.getReleasedDate());
        assertEquals(runtimeInMinutes, result.getRunTimeInMinutes());
    }
}