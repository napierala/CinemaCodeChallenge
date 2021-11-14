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
import pl.napierala.cinemacodechallenge.extmodel.UpdateTicketPricesRequest;
import pl.napierala.cinemacodechallenge.extmodel.UpdateTicketPricesResponse;
import pl.napierala.cinemacodechallenge.service.TicketPricesService;

import javax.validation.Valid;

@RestController
@RequestMapping("/cinema")
public class CinemaController {

    private TicketPricesService ticketPricesService;

    @Autowired
    public CinemaController(TicketPricesService ticketPricesService) {
        this.ticketPricesService = ticketPricesService;
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
        return ticketPricesService.updateTicketPrices(request);
    }
}