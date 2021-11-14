package pl.napierala.cinemacodechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovieShowingNotFoundException extends RuntimeException {

    public MovieShowingNotFoundException() {
    }

    public MovieShowingNotFoundException(String message) {
        super(message);
    }

    public MovieShowingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieShowingNotFoundException(Throwable cause) {
        super(cause);
    }

    public MovieShowingNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}