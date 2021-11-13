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
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The request to update a movie showing.")
public class UpdateMovieShowingRequest implements Serializable {

    private static final long serialVersionUID = -8276200681206438649L;

    @NotEmpty
    @Schema(description = "The uuid to identify which movie showing to update.")
    private String uuid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "The new start time of the movie, if it is null then the start time will not be updated.",
            type = "string", pattern = "yyyy-MM-dd'T'HH:mm:ss", example = "2004-11-26T16:00:00")
    private LocalDateTime dateTime;

    @Schema(description = "The new room where the movie will be shown, if it is null then the room will not be updated.")
    private String room;
}