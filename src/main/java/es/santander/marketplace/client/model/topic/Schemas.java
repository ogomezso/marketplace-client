package es.santander.marketplace.client.model.topic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Schemas {
    @JsonProperty("value.schema.file")
    private String schemaFile;
    @JsonProperty("value.compatibility")
    private String compatibility;

    @JsonProperty("value.format")
    private String format;
}
