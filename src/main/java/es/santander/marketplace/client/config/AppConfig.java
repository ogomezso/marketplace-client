package es.santander.marketplace.client.config;

import lombok.Data;

@Data
public class AppConfig {
    private String marketPlaceUrl;
    private String schemaRegistryUrl;
    private String registrationEndpoint;
    private String subscriptionEndpoint;
}
