package pl.napierala.cinemacodechallenge.extmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The request to update ticket prices.")
public class UpdateTicketPricesRequest implements Serializable {

    private static final long serialVersionUID = -8276200681206438649L;

    @NotEmpty
    @Schema(description = "The cinema code to update the ticket prices for.")
    private String cinemaCode;

    @ArraySchema(schema = @Schema(description = "The ticket prices."))
    private List<TicketPricesRequest> ticketPrices;
}