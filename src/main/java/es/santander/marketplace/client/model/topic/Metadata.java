package es.santander.marketplace.client.model.topic;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Metadata {
    private String eventName;
    private String eventDescription;
    private String eventExample;
    private String topicDescription;
    private String topicType;
    private String topicConfidentialityData;
    private String topicPlatform;
    private String topicStatus;
    private String topicCDCSource;
    private String topicCategory;
}
