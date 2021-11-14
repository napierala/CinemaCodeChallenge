package pl.napierala.cinemacodechallenge.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.napierala.cinemacodechallenge.imdb.IMDBMovie;

@FeignClient(value = "omdbAPI", url = "http://www.omdbapi.com")
public interface OMDBApiRepository {

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    IMDBMovie find(@RequestParam(value = "apiKey") String apiKey, @RequestParam(value = "i") String imdbId);
}