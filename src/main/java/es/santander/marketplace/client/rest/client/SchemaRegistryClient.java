package es.santander.marketplace.client.rest.client;

import es.santander.marketplace.client.config.AppConfig;
import es.santander.marketplace.client.rest.model.SchemaResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SchemaRegistryClient {

    private Client client = ClientBuilder.newClient();

    public SchemaResponse getSchemaInfo(AppConfig appConfig, String subject){
     return client.
     target(appConfig.getSchemaRegistryUrl()+"/subjects/" + subject + "/versions/latest")
     .request(MediaType.APPLICATION_JSON)
     .accept(MediaType.APPLICATION_JSON)
     .get(SchemaResponse.class);
    }
}