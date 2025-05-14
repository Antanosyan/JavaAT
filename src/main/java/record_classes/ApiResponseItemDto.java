package record_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponseItemDto(
        String id,
        String name,
        Data data
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Data(
            String color,
            String Color,

            String capacity,
            @JsonProperty("capacity GB")
            Integer capacityGB,

            Double price,
            String generation,
            Integer year,

            @JsonProperty("CPU model")
            String cpuModel,

            @JsonProperty("Hard disk size")
            String hardDiskSize,

            @JsonProperty("Strap Colour")
            String strapColour,

            @JsonProperty("Case Size")
            String caseSize,

            @JsonProperty("Screen size")
            Double screenSize,

            @JsonProperty("Generation")
            String generationLabel,

            @JsonProperty("Price")
            String priceLabel,

            @JsonProperty("Capacity")
            String capacityLabel,

            @JsonProperty("Description")
            String description
    ) {}
}
