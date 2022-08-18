package es.santander.marketplace.client.rest.client;

import es.santander.marketplace.client.config.AppConfig;
import es.santander.marketplace.client.rest.model.Subscription;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class SubscriptionClient {

    private Client client = ClientBuilder.newClient();

    public Response createNewTopicSubscription(Subscription subscription, AppConfig appConfig) {
        return client
                .target(appConfig.getMarketPlaceUrl() + appConfig.getSubscriptionEndpoint())
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(subscription, MediaType.APPLICATION_JSON));
    }
}
