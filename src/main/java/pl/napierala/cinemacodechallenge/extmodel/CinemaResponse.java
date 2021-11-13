package pl.napierala.cinemacodechallenge.extmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "A single movie showing.")
public class CinemaResponse implements Serializable {

    private static final long serialVersionUID = 7136542451252764969L;

    @Schema(description = "The cinema code.")
    private String code;

    @Schema(description = "The cinema name.")
    private String name;

    @Schema(description = "The cinema address.")
    private String address;
}