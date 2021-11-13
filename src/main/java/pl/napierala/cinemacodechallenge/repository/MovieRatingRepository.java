package pl.napierala.cinemacodechallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.napierala.cinemacodechallenge.entity.MovieEntity;
import pl.napierala.cinemacodechallenge.entity.MovieRatingEntity;
import pl.napierala.cinemacodechallenge.entity.UserEntity;

import java.util.Optional;

public interface MovieRatingRepository extends JpaRepository<MovieRatingEntity, Long> {
    Optional<MovieRatingEntity> findByMovieAndUser(MovieEntity movie, UserEntity user);
}