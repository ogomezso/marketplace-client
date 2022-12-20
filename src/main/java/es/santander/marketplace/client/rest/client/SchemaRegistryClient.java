package es.santander.marketplace.client.rest.client;

import javax.net.ssl.SSLContext;

import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import es.santander.marketplace.client.config.AppConfig;
import es.santander.marketplace.client.rest.model.SchemaResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SchemaRegistryClient {

  public SchemaResponse getSchemaInfo(AppConfig appConfig, String subject) {

    String targetUrl = appConfig.getSchemaRegistryUrl() + "/subjects/" + subject + "/versions/latest";
    SchemaResponse response;
    try {
      response = createSchemaRegistryClient(appConfig).target(targetUrl)
              .request(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON)
              .get(SchemaResponse.class);
    } catch (Exception e) {
      System.out.println("Error getting subject from Schema Registry");
      response = SchemaResponse.builder().build();
    }
    return response;
  }

  private Client createSchemaRegistryClient(AppConfig appConfig) {
    HttpAuthenticationFeature basicAuth = HttpAuthenticationFeature.basic(appConfig.getSchemaRegistryUser(),
        appConfig.getSchemaRegistryPassword());
    return ClientBuilder.newBuilder()
        .sslContext(getSslContext(appConfig))
        .build()
        .register(JacksonFeature.class)
        .register(basicAuth);
  }

  private SSLContext getSslContext(AppConfig appConfig) {
    return SslConfigurator.newInstance()
        .trustStoreFile(appConfig.getSchemaRegistryTrustStorePath())
        .trustStorePassword(appConfig.getSchemaRegistryTrustStorePassword())
        .keyStoreFile(appConfig.getSchemaRegistryKeyStorePath())
        .keyStorePassword(appConfig.getSchemaRegistryKeyStorePassword())
        .createSSLContext();
  }
}