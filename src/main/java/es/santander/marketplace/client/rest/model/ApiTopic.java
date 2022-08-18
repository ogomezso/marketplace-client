package es.santander.marketplace.client.rest.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiTopic {
    @Builder.Default
    private Integer topicCodeId = 0;
    private String topicName;
    private String topicDescription;
    @Builder.Default
    private Integer topicFormData = 0;
    private String topicCreationDate;
    private String topicType;
    private Integer topicStatus;
    private String topicConfidentialityData;
    private String topicPartitions;
    private Integer topicTTL;
    private String topicPlatform;
    private String topicCDCsourceTable;
    @Builder.Default
    private Integer topicIdApplication = 0;
}
