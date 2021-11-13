package pl.napierala.cinemacodechallenge.extmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The request for to rate a movie.")
public class RateAMovieRequest implements Serializable {

    private static final long serialVersionUID = -7996813454445560299L;

    @NotEmpty
    @Schema(description = "The movie code.")
    private String movieCode;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    @Schema(description = "The movie rating.")
    private Integer rating;
}