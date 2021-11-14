package pl.napierala.cinemacodechallenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.napierala.cinemacodechallenge.extmodel.*;
import pl.napierala.cinemacodechallenge.service.MovieShowingService;

import javax.validation.Valid;

@RestController
@RequestMapping("/movieShowing")
public class MovieShowingController {

    private MovieShowingService movieShowingService;

    @Autowired
    public MovieShowingController(MovieShowingService movieShowingService) {
        this.movieShowingService = movieShowingService;
    }

    @Operation(
            summary = "Find movie showings.",
            description = "Find movie showing in a particular cinema in a date range, results are ordered by the showing time ascending. Available without authentication.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Result",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FindMovieShowingsResponse.class)
                            )
                    )
            }
    )
    @RequestMapping(value = "/find", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public FindMovieShowingsResponse find(@RequestBody @Valid final FindMovieShowingsRequest request) {
        return movieShowingService.findAndOrderByShowingTimeAscending(request);
    }

    @Operation(
            summary = "Add a movie showing.",
            description = "Add a movie showing. Only available for Admins.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The movie showing that was added.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieShowingResponse.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public MovieShowingResponse addMovieShowing(@RequestBody @Valid final AddMovieShowingRequest request) {
        return movieShowingService.add(request);
    }

    @Operation(
            summary = "Delete a movie showing.",
            description = "Delete a movie showing. Only available for Admins.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The movie showing that was deleted.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieShowingResponse.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public MovieShowingResponse deleteMovieShowing(@RequestBody @Valid final DeleteMovieShowingRequest request) {
        return movieShowingService.delete(request);
    }

    @Operation(
            summary = "Update a movie showing.",
            description = "Update a movie showing. Only available for Admins.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The movie showing that was updated.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieShowingResponse.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public MovieShowingResponse updateMovieShowing(@RequestBody @Valid final UpdateMovieShowingRequest request) {
        return movieShowingService.update(request);
    }
}