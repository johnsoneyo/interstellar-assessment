package za.co.discovery.interstellarassessment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import za.co.discovery.interstellarassessment.bo.Planet;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Getter
public class RouteDto {

    @NotNull(message = "origin is required")
    Planet origin;
    @NotNull(message = "destination is required")
    Planet destination;
    @NotNull
    Double distance;

    @JsonCreator
    public RouteDto(@JsonProperty("origin") Planet origin,  @JsonProperty("destination") Planet destination, 
                    @JsonProperty("distance") Double distance) {
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
    }
}
