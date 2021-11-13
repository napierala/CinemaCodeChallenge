package pl.napierala.cinemacodechallenge;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.napierala.cinemacodechallenge.extmodel.UserLoginRequest;
import pl.napierala.cinemacodechallenge.extmodel.UserLoginResponse;
import pl.napierala.cinemacodechallenge.util.IntegrationTest;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Category(IntegrationTest.class)
public class SecurityIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldTestCorrectlyForAdminRoleOnlyAppInfo() throws Exception {

        shouldNotPassSecurity("/appInfo/version", "invalid_user", "test", String.class, null);
        shouldPassSecurity("/appInfo/version", "admin", "admin_password", String.class, null);
        shouldNotPassSecurity("/appInfo/version", "user", "user_password", String.class, null);
    }

    @Test
    public void shouldTestCorrectlyForLoginThatDoesntRequireAuthentication() throws Exception {

        UserLoginRequest request = UserLoginRequest.builder()
                .userName("user")
                .password("user_password")
                .build();

        shouldPassSecurity("/public/user/login", null, null, UserLoginResponse.class, request);
    }

    private <T> void shouldPassSecurity(String url, String user, String password, Class<T> responseClazz, Object request) throws Exception {
        testSecurity(url, true, user, password, responseClazz, request);
    }

    private <T> void shouldNotPassSecurity(String url, String user, String password, Class<T> responseClazz, Object request) throws Exception {
        testSecurity(url, false, user, password, responseClazz, request);
    }

    private <T> void testSecurity(String url, boolean pass, String user, String password, Class<T> responseClazz, Object request) throws Exception {

        // Given
        TestRestTemplate finalRestTemplate = this.restTemplate;

        if (user != null && password != null) {
            finalRestTemplate = finalRestTemplate.withBasicAuth(user, password);
        }

        // When
        ResponseEntity<T> response = null;

        if (request != null) {
            response = finalRestTemplate.postForEntity(url, request, responseClazz);
        } else {
            response = finalRestTemplate.getForEntity(url, responseClazz);
        }

        // Then
        assertNotNull(response);

        if (pass) {
            assertNotEquals(401, response.getStatusCodeValue());
        } else {
            assertTrue(Sets.newHashSet(401, 403).contains(response.getStatusCodeValue()));
        }
    }
}