package pl.napierala.cinemacodechallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.napierala.cinemacodechallenge.builder.TicketPriceEntityBuilder;
import pl.napierala.cinemacodechallenge.builder.UpdateTicketPricesResponseBuilder;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.entity.TicketPriceEntity;
import pl.napierala.cinemacodechallenge.exception.CinemaNotFoundException;
import pl.napierala.cinemacodechallenge.extmodel.UpdateTicketPricesRequest;
import pl.napierala.cinemacodechallenge.extmodel.UpdateTicketPricesResponse;
import pl.napierala.cinemacodechallenge.repository.TicketPriceRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TicketPricesService {

    private TicketPriceRepository ticketPriceRepository;
    private CinemaService cinemaService;

    @Autowired
    public TicketPricesService(TicketPriceRepository ticketPriceRepository, CinemaService cinemaService) {
        this.ticketPriceRepository = ticketPriceRepository;
        this.cinemaService = cinemaService;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public UpdateTicketPricesResponse updateTicketPrices(UpdateTicketPricesRequest request) {

        Optional<CinemaEntity> cinemaEntityOptional = cinemaService.findByCode(request.getCinemaCode());

        if (!cinemaEntityOptional.isPresent()) {
            throw new CinemaNotFoundException();
        }

        CinemaEntity cinema = cinemaEntityOptional.get();

        ticketPriceRepository.removeByCinema(cinema);

        List<TicketPriceEntity> ticketPriceEntities = TicketPriceEntityBuilder.buildWithList(request.getTicketPrices(), cinema);

        if (ticketPriceEntities != null) {
            for (TicketPriceEntity ticketPriceEntity : ticketPriceEntities) {
                ticketPriceRepository.save(ticketPriceEntity);
            }
        }

        return UpdateTicketPricesResponseBuilder.buildWith(cinema);
    }
}