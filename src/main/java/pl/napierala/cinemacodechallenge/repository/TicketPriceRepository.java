package pl.napierala.cinemacodechallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.TicketPriceEntity;

import java.util.List;

public interface TicketPriceRepository extends JpaRepository<TicketPriceEntity, Long> {
    List<TicketPriceEntity> findByCinema(CinemaEntity cinema);

    Long removeByCinema(CinemaEntity cinema);
}