package es.santander.marketplace.client.rest.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Event {
    private Integer eventCodeId;
    private String eventName;
    private Integer eventSchemaId;
    private Integer eventSchemaVersion;
    private Integer eventSchemaCompatibility;
    private String eventDescription;
    private ApiTopic topic;
}
