package pl.napierala.cinemacodechallenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.napierala.cinemacodechallenge.extmodel.MovieDetailsRequest;
import pl.napierala.cinemacodechallenge.extmodel.MovieDetailsResponse;
import pl.napierala.cinemacodechallenge.extmodel.RateAMovieRequest;
import pl.napierala.cinemacodechallenge.extmodel.RateAMovieResponse;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Operation(
            summary = "Get details about a movie.",
            description = "Details about a movie: rating if the user rated, and other information from IMDB. Available only for regular users.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Result",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDetailsResponse.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/details", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public MovieDetailsResponse details(@RequestBody @Valid final MovieDetailsRequest request, Principal principal) {
        return null; // TODO
    }

    @Operation(
            summary = "Rate a movie",
            description = "Rate a movie, a movie can only be rated once. Available only for regular users.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Result",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RateAMovieResponse.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/rate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public RateAMovieResponse rate(@RequestBody @Valid final RateAMovieRequest request, Principal principal) {
        return null; // TODO
    }
}