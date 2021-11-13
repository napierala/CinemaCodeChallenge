package pl.napierala.cinemacodechallenge.extmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "A single movie showing.")
public class MovieShowingResponse implements Serializable {

    private static final long serialVersionUID = 7136542451252764969L;

    @Schema(description = "The cinema.")
    private CinemaResponse cinema;

    @Schema(description = "The movie.")
    private BasicMovieResponse movie;

    @Schema(description = "The showing uuid.")
    private String uuid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "The time that the movie starts.",
            type = "string", pattern = "yyyy-MM-dd'T'HH:mm:ss", example = "2004-11-26T16:00:00")
    private LocalDateTime dateTime;

    @Schema(description = "The room that the movie is showing.")
    private String room;
}