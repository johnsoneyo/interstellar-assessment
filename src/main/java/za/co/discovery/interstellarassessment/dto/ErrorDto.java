package za.co.discovery.interstellarassessment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ErrorDto {

    String objectName;
    String field;
    String message;

    @JsonCreator
    public ErrorDto(@JsonProperty("objectName") String objectName, @JsonProperty("field") String field,
                    @JsonProperty("message") String message) {
        this.objectName = objectName;
        this.field = field;
        this.message = message;
    }

    @JsonCreator
    public ErrorDto(@JsonProperty("message") String message) {
        this.message = message;
    }
}
