package pl.napierala.cinemacodechallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.MovieShowingEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface MovieShowingRepository extends JpaRepository<MovieShowingEntity, Long> {

    @Query(value = "FROM MovieShowingEntity ms WHERE cinema = :cinema AND dateTime BETWEEN :startDate AND :endDate")
    List<MovieShowingEntity> findByCinemaAndOnTheDay(@Param("cinema") CinemaEntity cinema,
                                                     @Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);
}