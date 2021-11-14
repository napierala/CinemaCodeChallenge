package pl.napierala.cinemacodechallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.napierala.cinemacodechallenge.builder.FindMovieShowingsResponseBuilder;
import pl.napierala.cinemacodechallenge.builder.MovieShowingEntityBuilder;
import pl.napierala.cinemacodechallenge.builder.MovieShowingResponseBuilder;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.MovieEntity;
import pl.napierala.cinemacodechallenge.entity.MovieShowingEntity;
import pl.napierala.cinemacodechallenge.exception.MovieShowingNotFoundException;
import pl.napierala.cinemacodechallenge.extmodel.*;
import pl.napierala.cinemacodechallenge.repository.MovieShowingRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MovieShowingService {

    private MovieShowingRepository movieShowingRepository;
    private CinemaService cinemaService;
    private MovieService movieService;

    @Autowired
    public MovieShowingService(MovieShowingRepository movieShowingRepository, CinemaService cinemaService, MovieService movieService) {
        this.movieShowingRepository = movieShowingRepository;
        this.cinemaService = cinemaService;
        this.movieService = movieService;
    }

    public FindMovieShowingsResponse findAndOrderByShowingTimeAscending(FindMovieShowingsRequest request) {

        CinemaEntity cinemaEntity = cinemaService.findByCodeOrThrowException(request.getCinemaCode());

        List<MovieShowingEntity> movieShowings = movieShowingRepository.findByCinemaAndOnTheDay(cinemaEntity, request.getFromDateTime(), request.getToDateTime());

        FindMovieShowingsResponse result = FindMovieShowingsResponseBuilder.buildWithList(movieShowings);

        // Order it
        result.getMovieShowings().sort(Comparator.comparing(MovieShowingResponse::getDateTime));

        return result;
    }

    public MovieShowingResponse add(AddMovieShowingRequest request) {

        CinemaEntity cinemaEntity = cinemaService.findByCodeOrThrowException(request.getCinemaCode());
        MovieEntity movieEntity = movieService.findByCodeOrThrowException(request.getMovieCode());

        MovieShowingEntity movieShowingEntity = MovieShowingEntityBuilder.buildWith(cinemaEntity, movieEntity, request);

        MovieShowingEntity afterPersist = movieShowingRepository.save(movieShowingEntity);
        return MovieShowingResponseBuilder.buildWith(afterPersist);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public MovieShowingResponse delete(DeleteMovieShowingRequest request) {

        String uuid = request.getUuid();

        Optional<MovieShowingEntity> movieShowingEntityOptional = movieShowingRepository.findByUuid(uuid);

        if (!movieShowingEntityOptional.isPresent()) {
            throw new MovieShowingNotFoundException();
        }

        MovieShowingEntity movieShowingEntity = movieShowingEntityOptional.get();

        movieShowingRepository.removeByUuid(uuid);
        return MovieShowingResponseBuilder.buildWith(movieShowingEntity);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public MovieShowingResponse update(UpdateMovieShowingRequest request) {

        String uuid = request.getUuid();

        Optional<MovieShowingEntity> movieShowingEntityOptional = movieShowingRepository.findByUuid(uuid);

        if (!movieShowingEntityOptional.isPresent()) {
            throw new MovieShowingNotFoundException();
        }

        MovieShowingEntity movieShowingEntity = movieShowingEntityOptional.get();

        LocalDateTime dateTime = request.getDateTime();
        String room = request.getRoom();

        if (dateTime != null) {
            movieShowingEntity.setDateTime(dateTime);
        }

        if (room != null) {
            movieShowingEntity.setRoom(room);
        }

        MovieShowingEntity afterPersist = movieShowingRepository.save(movieShowingEntity);

        return MovieShowingResponseBuilder.buildWith(afterPersist);
    }
}