package pl.napierala.cinemacodechallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.napierala.cinemacodechallenge.imdb.IMDBMovie;
import pl.napierala.cinemacodechallenge.repository.OMDBApiRepository;

@Service
public class OMDBService {

    private String apiKey;
    private OMDBApiRepository omdbApiRepository;

    @Autowired
    public OMDBService(@Value("${imdbKey:null}") String apiKey, OMDBApiRepository omdbApiRepository) {
        this.omdbApiRepository = omdbApiRepository;
        this.apiKey = apiKey;
    }

    @Cacheable(value = "imdb")
    public IMDBMovie fetch(String imdbId) {
        return omdbApiRepository.find(apiKey, imdbId);
    }
}