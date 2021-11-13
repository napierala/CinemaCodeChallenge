package pl.napierala.cinemacodechallenge.extmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The request to find movie showings.")
public class FindMovieShowingsRequest implements Serializable {

    private static final long serialVersionUID = -8276200681206438649L;

    @NotEmpty
    @Schema(description = "The cinema code to find the movie showings for.")
    private String cinemaCode;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "The start of the time interval to find the movie showings for.",
            type = "string", pattern = "yyyy-MM-dd'T'HH:mm:ss", example = "2004-11-26T16:00:00")
    private LocalDateTime fromDateTime;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "The end of the time interval to find the movie showings for.",
            type = "string", pattern = "yyyy-MM-dd'T'HH:mm:ss", example = "2004-11-26T16:00:00")
    private LocalDateTime toDateTime;

    @AssertTrue
    @JsonIgnore
    private boolean isTheRangeCorrect() {

        if (fromDateTime == null || toDateTime == null) {
            return false;
        }

        if (toDateTime.isBefore(fromDateTime)) {
            return false;
        }

        long noOfDaysInTheRange = fromDateTime.until(toDateTime, ChronoUnit.DAYS);

        return (noOfDaysInTheRange < 8); // MAX no of days, to protect against large queries
    }
}