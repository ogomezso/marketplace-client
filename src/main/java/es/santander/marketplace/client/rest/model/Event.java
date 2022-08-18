package es.santander.marketplace.client.rest.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Event {
    @Builder.Default
    private Integer eventCodeId = 0;
    private String eventName;
    private String eventSchemaId;
    private String eventSchemaVersion;
    private String eventSchemaCompatibility;
    private String eventDescription;
    private ApiTopic topic;
}
