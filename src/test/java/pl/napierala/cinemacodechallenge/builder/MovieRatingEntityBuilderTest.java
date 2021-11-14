package pl.napierala.cinemacodechallenge.builder;

import com.google.common.collect.Sets;
import org.junit.Test;
import pl.napierala.cinemacodechallenge.entity.MovieEntity;
import pl.napierala.cinemacodechallenge.entity.MovieRatingEntity;
import pl.napierala.cinemacodechallenge.entity.UserEntity;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MovieRatingEntityBuilderTest {

    @Test
    public void shouldBuildCorrectly() {

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

        Integer rating = 3;

        // When
        MovieRatingEntity result = MovieRatingEntityBuilder.buildWith(movieEntity, userEntity, rating);

        // Then
        assertNotNull(result);
        assertEquals(movieEntity, result.getMovie());
        assertEquals(userEntity, result.getUser());
        assertEquals(rating, result.getRating());
    }
}