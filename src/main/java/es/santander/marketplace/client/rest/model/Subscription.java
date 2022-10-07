package es.santander.marketplace.client.rest.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Subscription {
    String appkey;
    String topicName;
    Integer subtCodeId;
    Integer subtAppCodeId;
    Integer subtTopicCodeId;
    String subtConsumerGroup;
    Integer subtType;
    Integer subtStatus;
    Integer subtUserCodeIdRequest;
    Integer subtUserCodeIdApproval;
}
