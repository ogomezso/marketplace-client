package es.santander.marketplace.client.rest.mapper;

import es.santander.marketplace.client.model.topic.Consumer;
import es.santander.marketplace.client.model.topic.Producer;
import es.santander.marketplace.client.rest.model.Subscription;

public class SubscriptionRequestMapper {
    
    public Subscription toConsumerSubscription(String topicName, Consumer consumer){

        return Subscription.builder()
        .appkey(extractAppKey(consumer.getPrincipal()))
        .topicName(topicName)
        .subscriptionType("consumer")
        .consumerGroup(consumer.getGroup())
        .build();
    }

    public Subscription toProducerSubscription(String topicName, Producer producer){
        return Subscription.builder()
        .appkey(extractAppKey(producer.getPrincipal()))
        .topicName(topicName)
        .subscriptionType("producer")
        .consumerGroup("NA")
        .build();
    }

    private String extractAppKey(String principal) {
    String sub = principal.substring(principal.lastIndexOf("apl_"));
    return sub;
    }
}
