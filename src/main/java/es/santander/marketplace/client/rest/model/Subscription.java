package es.santander.marketplace.client.rest.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Subscription {
    @Builder.Default
    Integer appCodeId = 0;
    String appkey;
    @Builder.Default
    Integer topicCodeId = 0;
    String topicName;
    String subscriptionType;
    String consumerGroup;
    @Builder.Default
    Integer status = 0;
}
