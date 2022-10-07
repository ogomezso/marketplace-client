package es.santander.marketplace.client.model.topic;

import lombok.Data;

@Data
public class Metadata {
    private String eventName;
    private String eventDescription;
    private String eventContent;
    private String topicDescription;
    private String topicType;
    private String topicConfidentiality;
    private String topicPlatform;
    private String topicStatus;
    private String topicSourceTableCDC;
    private String topicCategory;
}
