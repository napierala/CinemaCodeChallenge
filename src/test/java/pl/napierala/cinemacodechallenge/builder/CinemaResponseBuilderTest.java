package pl.napierala.cinemacodechallenge.builder;

import org.junit.Test;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.extmodel.CinemaResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CinemaResponseBuilderTest {

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

        // When
        CinemaResponse result = CinemaResponseBuilder.buildWith(cinemaEntity);

        // Then
        assertNotNull(result);
        assertEquals(code, result.getCode());
        assertEquals(name, result.getName());
        assertEquals(address, result.getAddress());
    }
}