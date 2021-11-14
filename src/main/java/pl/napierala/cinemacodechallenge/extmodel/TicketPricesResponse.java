package pl.napierala.cinemacodechallenge.extmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Ticket prices.")
public class TicketPricesResponse implements Serializable {

    private static final long serialVersionUID = -7996813454445560299L;

    @Schema(description = "A description - for example: normal ticket, discounted for students etc.")
    private String description;

    @Min(value = 0)
    @Schema(description = "The price in cents meaning that $10.50 will be 1050 as it is in cents. The concept of cents is just a name as it could be grosz in PLN, pennies for the pound or euro cents.")
    private Integer priceInCents;
}