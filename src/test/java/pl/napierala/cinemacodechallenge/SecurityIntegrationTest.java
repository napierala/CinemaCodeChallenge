package pl.napierala.cinemacodechallenge;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpStatusCodeException;
import pl.napierala.cinemacodechallenge.extmodel.*;
import pl.napierala.cinemacodechallenge.util.IntegrationTest;

import java.net.HttpRetryException;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Category(IntegrationTest.class)
public class SecurityIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final Set<Integer> NOT_AUTHORIZED_HTTP_CODES = Sets.newHashSet(401, 403);

    @Test
    public void shouldTestCorrectlyForTheAppInfoController() throws Exception {

        shouldNotPassSecurity("/appInfo/version", HttpMethod.GET, "invalid_user", "test", String.class);
        shouldNotPassSecurity("/appInfo/version", HttpMethod.GET, "user", "user_password", String.class);
        shouldPassSecurity("/appInfo/version", HttpMethod.GET, "admin", "admin_password", String.class);
    }

    @Test
    public void shouldTestCorrectlyForTheUsersController() throws Exception {

        UserLoginRequest request = UserLoginRequest.builder()
                .userName("user")
                .password("user_password")
                .build();

        shouldPassSecurity("/public/user/login", HttpMethod.POST, null, null, UserLoginResponse.class, request);
        shouldPassSecurity("/public/user/register", HttpMethod.POST, null, null, UserLoginResponse.class, request);
    }

    @Test
    public void shouldTestCorrectlyForTheCinemaController() throws Exception {

        UpdateTicketPricesRequest request = UpdateTicketPricesRequest.builder()
                .cinemaCode("INVALID_CINEMA")
                .build();

        shouldNotPassSecurity("/cinema/updateTicketPrices", HttpMethod.POST, null, null, UpdateTicketPricesResponse.class, request);
        shouldNotPassSecurity("/cinema/updateTicketPrices", HttpMethod.POST, "user", "user_password", UpdateTicketPricesResponse.class, request);
        shouldPassSecurity("/cinema/updateTicketPrices", HttpMethod.POST, "admin", "admin_password", UpdateTicketPricesResponse.class, request);
    }

    @Test
    public void shouldTestCorrectlyForTheMovieController() throws Exception {

        MovieDetailsRequest movieDetailsRequest = MovieDetailsRequest.builder()
                .movieCode("INVALID_MOVIE")
                .build();

        shouldNotPassSecurity("/movie/details", HttpMethod.POST, null, null, MovieDetailsResponse.class, movieDetailsRequest);
        shouldPassSecurity("/movie/details", HttpMethod.POST, "user", "user_password", MovieDetailsResponse.class, movieDetailsRequest);

        RateAMovieRequest rateAMovieRequest = RateAMovieRequest.builder()
                .movieCode("INVALID_MOVIE")
                .rating(1)
                .build();

        shouldNotPassSecurity("/movie/rate", HttpMethod.POST, null, null, RateAMovieResponse.class, rateAMovieRequest);
        shouldPassSecurity("/movie/rate", HttpMethod.POST, "user", "user_password", RateAMovieResponse.class, rateAMovieRequest);
    }

    @Test
    public void shouldTestCorrectlyForMovieShowingController() throws Exception {

        FindMovieShowingsRequest findMovieShowingsRequest = FindMovieShowingsRequest.builder()
                .cinemaCode("INVALID_CINEMA")
                .fromDateTime(LocalDateTime.now())
                .toDateTime(LocalDateTime.now())
                .build();

        shouldPassSecurity("/movieShowing/find", HttpMethod.POST, null, null, MovieDetailsResponse.class, findMovieShowingsRequest);

        AddMovieShowingRequest addMovieShowingRequest = AddMovieShowingRequest.builder()
                .cinemaCode("INVALID_CINEMA")
                .movieCode("INVALID_MOVIE")
                .dateTime(LocalDateTime.now())
                .room("INVALID_ROOM")
                .build();

        shouldNotPassSecurity("/movieShowing/add", HttpMethod.POST, null, null, MovieShowingResponse.class, addMovieShowingRequest);
        shouldNotPassSecurity("/movieShowing/add", HttpMethod.POST, "user", "user_password", MovieShowingResponse.class, addMovieShowingRequest);
        shouldPassSecurity("/movieShowing/add", HttpMethod.POST, "admin", "admin_password", MovieShowingResponse.class, addMovieShowingRequest);

        DeleteMovieShowingRequest deleteMovieShowingRequest = DeleteMovieShowingRequest.builder()
                .uuid("INVALID_UUID")
                .build();

        shouldNotPassSecurity("/movieShowing/delete", HttpMethod.DELETE, null, null, MovieShowingResponse.class, deleteMovieShowingRequest);
        shouldNotPassSecurity("/movieShowing/delete", HttpMethod.DELETE, "user", "user_password", MovieShowingResponse.class, deleteMovieShowingRequest);
        shouldPassSecurity("/movieShowing/delete", HttpMethod.DELETE, "admin", "admin_password", MovieShowingResponse.class, deleteMovieShowingRequest);

        UpdateMovieShowingRequest updateMovieShowingRequest = UpdateMovieShowingRequest.builder()
                .uuid("INVALID_UUID")
                .build();

        shouldNotPassSecurity("/movieShowing/update", HttpMethod.PUT, null, null, MovieShowingResponse.class, updateMovieShowingRequest);
        shouldNotPassSecurity("/movieShowing/update", HttpMethod.PUT, "user", "user_password", MovieShowingResponse.class, updateMovieShowingRequest);
        shouldPassSecurity("/movieShowing/update", HttpMethod.PUT, "admin", "admin_password", MovieShowingResponse.class, updateMovieShowingRequest);
    }

    private <T> void shouldPassSecurity(String url, HttpMethod httpMethod, String user, String password, Class<T> responseClazz, Object request) throws Exception {
        testSecurity(url, httpMethod, true, user, password, responseClazz, request);
    }

    private <T> void shouldPassSecurity(String url, HttpMethod httpMethod, String user, String password, Class<T> responseClazz) throws Exception {
        shouldPassSecurity(url, httpMethod, user, password, responseClazz, null);
    }

    private <T> void shouldNotPassSecurity(String url, HttpMethod httpMethod, String user, String password, Class<T> responseClazz, Object request) throws Exception {
        testSecurity(url, httpMethod, false, user, password, responseClazz, request);
    }


    private <T> void shouldNotPassSecurity(String url, HttpMethod httpMethod, String user, String password, Class<T> responseClazz) throws Exception {
        shouldNotPassSecurity(url, httpMethod, user, password, responseClazz, null);
    }

    private <T> void testSecurity(String url, HttpMethod httpMethod, boolean pass, String user, String password, Class<T> responseClazz, Object request) throws Exception {

        // Given
        TestRestTemplate finalRestTemplate = this.restTemplate;

        if (user != null && password != null) {
            finalRestTemplate = finalRestTemplate.withBasicAuth(user, password);
        }

        // When
        Integer responseCode = null;

        try {
            responseCode = finalRestTemplate.exchange(url, httpMethod, new HttpEntity<>(request), responseClazz).getStatusCodeValue();
        } catch (Exception e) {
            if (e.getCause() instanceof HttpRetryException) {
                HttpRetryException httpRetryException = (HttpRetryException) e.getCause();
                responseCode = httpRetryException.responseCode();
            } else if (e.getCause() instanceof HttpStatusCodeException) {
                HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) e.getCause();
                responseCode = httpStatusCodeException.getStatusCode().value();
            } else {
                throw e;
            }
        }

        // Then
        assertNotNull(responseCode);

        boolean notAuthorizedHttpCode = NOT_AUTHORIZED_HTTP_CODES.contains(responseCode);

        if (pass) {
            assertFalse(notAuthorizedHttpCode);
        } else {
            assertTrue(notAuthorizedHttpCode);
        }
    }
}