package es.santander.marketplace.client.rest.client;

import es.santander.marketplace.client.config.AppConfig;
import es.santander.marketplace.client.filehandler.YamlToPojo;
import es.santander.marketplace.client.rest.model.TopicRegistration;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class RegistrationClient {

    private Client client = ClientBuilder.newClient();

    public Response registerNewTopic(TopicRegistration registration, AppConfig appConfig) {
        return client
                .target(appConfig.getMarketPlaceUrl() + appConfig.getRegistrationEndpoint())
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(registration, MediaType.APPLICATION_JSON));
    }
}
