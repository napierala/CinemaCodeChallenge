package pl.napierala.cinemacodechallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.MovieShowingEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovieShowingRepository extends JpaRepository<MovieShowingEntity, Long> {

    Optional<MovieShowingEntity> findByUuid(String uuid);

    Long removeByUuid(String uuid);

    @Query(value = "FROM MovieShowingEntity ms WHERE cinema = :cinema AND dateTime BETWEEN :startDate AND :endDate")
    List<MovieShowingEntity> findByCinemaAndOnTheDay(@Param("cinema") CinemaEntity cinema,
                                                     @Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);
}