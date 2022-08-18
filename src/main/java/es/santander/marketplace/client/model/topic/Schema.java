package es.santander.marketplace.client.model.topic;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Schema {
    @JsonProperty("value.schema.file")
    private String schemaFile;
    @JsonProperty("value.compatibility")
    private String compatibility;
}
