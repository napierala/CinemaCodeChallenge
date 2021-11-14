package pl.napierala.cinemacodechallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.napierala.cinemacodechallenge.builder.CinemaDetailsResponseBuilder;
import pl.napierala.cinemacodechallenge.builder.TicketPriceEntityBuilder;
import pl.napierala.cinemacodechallenge.builder.UpdateTicketPricesResponseBuilder;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.TicketPriceEntity;
import pl.napierala.cinemacodechallenge.exception.CinemaNotFoundException;
import pl.napierala.cinemacodechallenge.extmodel.CinemaDetailsRequest;
import pl.napierala.cinemacodechallenge.extmodel.CinemaDetailsResponse;
import pl.napierala.cinemacodechallenge.extmodel.UpdateTicketPricesRequest;
import pl.napierala.cinemacodechallenge.extmodel.UpdateTicketPricesResponse;
import pl.napierala.cinemacodechallenge.repository.CinemaRepository;
import pl.napierala.cinemacodechallenge.repository.TicketPriceRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CinemaService {

    private CinemaRepository cinemaRepository;
    private TicketPriceRepository ticketPriceRepository;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository, TicketPriceRepository ticketPriceRepository) {
        this.cinemaRepository = cinemaRepository;
        this.ticketPriceRepository = ticketPriceRepository;
    }

    public Optional<CinemaEntity> findByCode(String code) {
        return cinemaRepository.findByCode(code);
    }

    public CinemaEntity findByCodeOrThrowException(String code) {
        return cinemaRepository.findByCode(code).orElseThrow(CinemaNotFoundException::new);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public UpdateTicketPricesResponse updateTicketPrices(UpdateTicketPricesRequest request) {

        CinemaEntity cinema = findByCodeOrThrowException(request.getCinemaCode());

        ticketPriceRepository.removeByCinema(cinema);

        List<TicketPriceEntity> ticketPriceEntities = TicketPriceEntityBuilder.buildWithList(request.getTicketPrices(), cinema);

        if (ticketPriceEntities != null) {
            for (TicketPriceEntity ticketPriceEntity : ticketPriceEntities) {
                ticketPriceRepository.save(ticketPriceEntity);
            }
        }

        return UpdateTicketPricesResponseBuilder.buildWith(cinema);
    }

    public CinemaDetailsResponse details(CinemaDetailsRequest request) {

        CinemaEntity cinemaEntity = findByCodeOrThrowException(request.getCinemaCode());

        List<TicketPriceEntity> ticketPriceEntities = ticketPriceRepository.findByCinema(cinemaEntity);

        return CinemaDetailsResponseBuilder.buildWith(cinemaEntity, ticketPriceEntities);
    }
}