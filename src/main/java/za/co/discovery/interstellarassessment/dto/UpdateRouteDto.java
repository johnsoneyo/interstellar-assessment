package za.co.discovery.interstellarassessment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import za.co.discovery.interstellarassessment.bo.Planet;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class UpdateRouteDto {

    Planet origin;
    Planet destination;
    Double distance;

    /**
     * An expliicit constructor is declared with {@link JsonCreator} for mapping and to maintain immutability
     * @param origin
     * @param destination
     * @param distance
     */
    @JsonCreator
    public UpdateRouteDto(@JsonProperty("origin") Planet origin, @JsonProperty("destination") Planet destination,
                    @JsonProperty("distance") Double distance) {
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
    }
}
