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
@Schema(description = "The response of the movie showings finding.")
public class FindMovieShowingsResponse implements Serializable {

    private static final long serialVersionUID = 1404513401184123631L;

    @ArraySchema(schema = @Schema(description = "Movie showings found for the request, ordered by the showing time ascending."))
    private List<MovieShowingResponse> movieShowings;
}