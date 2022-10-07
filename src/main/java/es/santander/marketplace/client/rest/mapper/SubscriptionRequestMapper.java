package es.santander.marketplace.client.rest.mapper;

import es.santander.marketplace.client.model.topic.Consumer;
import es.santander.marketplace.client.model.topic.Producer;
import es.santander.marketplace.client.rest.model.Subscription;

public class SubscriptionRequestMapper {

    private static final Integer CONSUMER = 1;
    private static final Integer PRODUCER = 0;
    private static final String PRINCIPAL_PREFIX = "apl_";

    public Subscription toConsumerSubscription(String topicName, Consumer consumer){

        return Subscription.builder()
        .appkey(extractAppKey(consumer.getPrincipal()))
        .topicName(topicName)
        .subtType(CONSUMER)
        .subtConsumerGroup(consumer.getGroup())
        .build();
    }

    public Subscription toProducerSubscription(String topicName, Producer producer){
        return Subscription.builder()
        .appkey(extractAppKey(producer.getPrincipal()))
        .topicName(topicName)
        .subtType(PRODUCER)
        .subtConsumerGroup("NA")
        .build();
    }

    private String extractAppKey(String principal) {
        return principal.substring(principal.lastIndexOf(PRINCIPAL_PREFIX));
    }
}
