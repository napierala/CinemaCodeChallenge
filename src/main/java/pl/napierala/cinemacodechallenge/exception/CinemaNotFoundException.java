package pl.napierala.cinemacodechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CinemaNotFoundException extends RuntimeException {

    public CinemaNotFoundException() {
    }

    public CinemaNotFoundException(String message) {
        super(message);
    }

    public CinemaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CinemaNotFoundException(Throwable cause) {
        super(cause);
    }

    public CinemaNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}