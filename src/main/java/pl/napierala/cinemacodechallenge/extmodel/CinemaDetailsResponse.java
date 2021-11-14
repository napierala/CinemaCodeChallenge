package pl.napierala.cinemacodechallenge.extmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The response for the movie details.")
public class CinemaDetailsResponse implements Serializable {

    private static final long serialVersionUID = 6744943293836698192L;

    @Schema(description = "The cinema code.")
    private String code;

    @Schema(description = "The cinema name.")
    private String name;

    @Schema(description = "The cinema address.")
    private String address;

    @ArraySchema(schema = @Schema(description = "The ticket prices for this cinema."))
    private List<TicketPricesResponse> ticketPrices;
}