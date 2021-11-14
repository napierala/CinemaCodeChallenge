package pl.napierala.cinemacodechallenge.builder;

import org.junit.Test;
import pl.napierala.cinemacodechallenge.extmodel.RateAMovieResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RateAMovieResponseBuilderTest {

    @Test
    public void shouldBuildCorrectlyForTrue() {

        // Given
        boolean successful = true;

        // When
        RateAMovieResponse result = RateAMovieResponseBuilder.buildWith(successful);

        // Then
        assertNotNull(result);
        assertEquals(successful, result.isSuccessful());
    }

    @Test
    public void shouldBuildCorrectlyForFalse() {

        // Given
        boolean successful = false;

        // When
        RateAMovieResponse result = RateAMovieResponseBuilder.buildWith(successful);

        // Then
        assertNotNull(result);
        assertEquals(successful, result.isSuccessful());
    }
}