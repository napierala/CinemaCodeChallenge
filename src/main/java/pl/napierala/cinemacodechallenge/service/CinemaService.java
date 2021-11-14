package pl.napierala.cinemacodechallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.napierala.cinemacodechallenge.entity.CinemaEntity;
import pl.napierala.cinemacodechallenge.repository.CinemaRepository;

import java.util.Optional;

@Service
public class CinemaService {

    private CinemaRepository cinemaRepository;

    @Autowired
    private CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public Optional<CinemaEntity> findByCode(String code) {
        return cinemaRepository.findByCode(code);
    }
}