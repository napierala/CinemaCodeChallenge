package pl.napierala.cinemacodechallenge.extmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The request to add a movie showing.")
public class AddMovieShowingRequest implements Serializable {

    private static final long serialVersionUID = -8276200681206438649L;

    @NotEmpty
    @Schema(description = "The cinema code where the movie showing will happen.")
    private String cinemaCode;

    @NotEmpty
    @Schema(description = "The movie code for the movie shown.")
    private String movieCode;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Movie start time.",
            type = "string", pattern = "yyyy-MM-dd'T'HH:mm:ss", example = "2004-11-26T16:00:00")
    private LocalDateTime dateTime;

    @NotNull
    @Schema(description = "The room that the movie will be shown.")
    private String room;
}