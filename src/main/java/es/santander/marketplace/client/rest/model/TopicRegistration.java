package es.santander.marketplace.client.rest.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TopicRegistration {
    private Application application;
    private Event event;
    private EventDocumentation eventDocumentation;
    private EventExample eventExample;
}
