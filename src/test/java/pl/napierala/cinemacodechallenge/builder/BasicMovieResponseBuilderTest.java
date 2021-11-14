package pl.napierala.cinemacodechallenge.builder;

import org.junit.Test;
import pl.napierala.cinemacodechallenge.entity.MovieEntity;
import pl.napierala.cinemacodechallenge.extmodel.BasicMovieResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BasicMovieResponseBuilderTest {

    @Test
    public void shouldBuildCorrectly() {

        // Given
        String code = "CODE";
        String name = "NAME";

        MovieEntity movieEntity = MovieEntity.builder()
                .code(code)
                .name(name)
                .imdbId("ID")
                .build();

        // When
        BasicMovieResponse result = BasicMovieResponseBuilder.buildWith(movieEntity);

        // Then
        assertNotNull(result);
        assertEquals(code, result.getCode());
        assertEquals(name, result.getName());
    }
}