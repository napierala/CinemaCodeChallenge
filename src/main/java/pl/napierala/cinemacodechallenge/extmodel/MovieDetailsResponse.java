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
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The response for the movie details.")
public class MovieDetailsResponse implements Serializable {

    private static final long serialVersionUID = -7996813454445560299L;

    @Schema(description = "The movie code.")
    private String code;

    @Schema(description = "The movie name.")
    private String name;

    @Schema(description = "The user's rating, 1-5 if the user didn't yet rate then null.")
    private Integer usersRating;

    @Schema(description = "The user's rating, 1-5 if the user didn't yet rate then null.")
    private Double imdbRating;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "The released date.")
    private LocalDate releasedDate;

    @Schema(description = "Runtime in minutes.")
    private Integer runTimeInMinutes;
}