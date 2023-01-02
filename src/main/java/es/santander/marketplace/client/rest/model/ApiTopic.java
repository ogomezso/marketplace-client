package es.santander.marketplace.client.rest.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiTopic {
    private Integer topicCodeId;
    private String topicName;
    private String topicDescription;
    private Integer topicFormatData;
    private String topicCreationDate;
    private Integer topicType;
    private Integer topicStatus;
    private Integer topicConfidentialityData;
    private Integer topicPartitions;
    private Integer topicTTL;
    private Integer topicPlatform;
    private String topicCDCsourceTable;
    private Integer topicIdApplication;
    private Integer topicCategory;
}
