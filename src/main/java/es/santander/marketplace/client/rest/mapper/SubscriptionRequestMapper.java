package es.santander.marketplace.client.rest.mapper;

import es.santander.marketplace.client.model.topic.Consumer;
import es.santander.marketplace.client.model.topic.Producer;
import es.santander.marketplace.client.rest.model.Subscription;

import java.util.Optional;

public class SubscriptionRequestMapper {

    private static final Integer CONSUMER = 1;
    private static final Integer PRODUCER = 0;
    private static final String PRINCIPAL_USER_PREFIX = "User:apl_";
    private static final String PRINCIPAL_GRP_PREFIX = "Group:grp_";

    public Subscription toConsumerSubscription(String topicName, Consumer consumer){

        return Subscription.builder()
        .appKey(extractAppKey(consumer.getPrincipal()))
        .topicName(topicName)
        .subtType(CONSUMER)
        .subtConsumerGroup(Optional.ofNullable(consumer.getGroup()).orElse(""))
        .build();
    }

    public Subscription toProducerSubscription(String topicName, Producer producer){
        return Subscription.builder()
        .appKey(extractAppKey(producer.getPrincipal()))
        .topicName(topicName)
        .subtType(PRODUCER)
        .subtConsumerGroup("")
        .build();
    }

    private String extractAppKey(String principal) {
        String[] principalChunks =  principal.split("_");

        return principalChunks[1];
    }
}
