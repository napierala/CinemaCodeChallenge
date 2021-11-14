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
import pl.napierala.cinemacodechallenge.extmodel.CinemaDetailsRequest;
import pl.napierala.cinemacodechallenge.extmodel.CinemaDetailsResponse;
import pl.napierala.cinemacodechallenge.extmodel.UpdateTicketPricesRequest;
import pl.napierala.cinemacodechallenge.extmodel.UpdateTicketPricesResponse;
import pl.napierala.cinemacodechallenge.service.CinemaService;

import javax.validation.Valid;

@RestController
@RequestMapping("/cinema")
public class CinemaController {

    private CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Operation(
            summary = "Update ticket prices.",
            description = "Update ticket prices for a particular cinema. Only available for Admins.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Result",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UpdateTicketPricesResponse.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/updateTicketPrices", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public UpdateTicketPricesResponse updateTicketPrices(@RequestBody @Valid final UpdateTicketPricesRequest request) {
        return cinemaService.updateTicketPrices(request);
    }

    @Operation(
            summary = "Details about a particular cinema.",
            description = "Find the details for a particular cinema. Available without authentication.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Result",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CinemaDetailsResponse.class)
                            )
                    )
            }
    )
    @RequestMapping(value = "/details", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public CinemaDetailsResponse details(@RequestBody @Valid final CinemaDetailsRequest request) {
        return cinemaService.details(request);
    }
}