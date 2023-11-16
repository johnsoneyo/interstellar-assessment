package za.co.discovery.assignment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import za.co.discovery.assignment.bo.Planet;

import javax.validation.constraints.NotNull;


/**
 * This dto is used for serialization and deserialization of route payloads
 * There are no setters in this class
 */
@Getter
public class RouteDto {

    @NotNull(message = "origin is required")
    Planet origin;
    @NotNull(message = "destination is required")
    Planet destination;
    @NotNull
    Double distance;

    /**
     * An expliicit constructor is declared with {@link JsonCreator} for mapping and to maintain immutability
     *
     * @param origin
     * @param destination
     * @param distance
     */
    @JsonCreator
    public RouteDto(@JsonProperty("origin") Planet origin, @JsonProperty("destination") Planet destination,
                    @JsonProperty("distance") Double distance) {
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
    }
}
