package pl.napierala.cinemacodechallenge.imdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
public class IMDBMovie implements Serializable {

    private static final long serialVersionUID = -1527290920723684637L;

    @JsonProperty("Title")
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMM yyyy", locale = "en-US")
    @JsonProperty("Released")
    private LocalDate released;

    @JsonProperty("imdbRating")
    private Double rating;

    @JsonDeserialize(using = RuntimeWithMinutesDeserializer.class)
    @JsonProperty("Runtime")
    private Integer runtimeInMinutes;
}