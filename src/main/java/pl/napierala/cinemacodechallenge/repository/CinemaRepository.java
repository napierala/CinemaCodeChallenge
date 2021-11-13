package pl.napierala.cinemacodechallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;

import java.util.Optional;

public interface CinemaRepository extends JpaRepository<CinemaEntity, Long> {
    Optional<CinemaEntity> findByCode(String code);
}