package es.santander.marketplace.client.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchemaResponse {

    private String subject;
    private Integer version;
    private Integer id;
    private String schemaType;
    private String schema;
}
