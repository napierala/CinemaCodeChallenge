package pl.napierala.cinemacodechallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.napierala.cinemacodechallenge.entity.MovieEntity;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    Optional<MovieEntity> findByCode(String code);
}