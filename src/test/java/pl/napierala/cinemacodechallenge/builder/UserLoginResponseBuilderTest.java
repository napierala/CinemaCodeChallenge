package pl.napierala.cinemacodechallenge.builder;

import org.junit.Test;
import pl.napierala.cinemacodechallenge.entity.UserEntity;
import pl.napierala.cinemacodechallenge.extmodel.UserLoginResponse;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserLoginResponseBuilderTest {

    @Test
    public void shouldCorrectlyCopyTheTimeToTheResponse() {

        // Given
        String userName = "Harry";
        String password = "Potter";
        LocalDateTime createTime = LocalDateTime.of(2004, 11, 26, 8, 0);

        UserEntity userEntity = UserEntity.builder()
                .userName(userName)
                .password(password)
                .createTime(createTime)
                .build();

        // When
        UserLoginResponse result = UserLoginResponseBuilder.buildWith(userEntity);

        // Then
        assertNotNull(result);
        assertEquals(createTime, result.getCreateTime());
    }
}