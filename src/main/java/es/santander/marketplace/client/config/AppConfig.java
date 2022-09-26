package es.santander.marketplace.client.config;

import lombok.Data;

@Data
public class AppConfig {
    private String schemaRegistryUrl;
    private String schemaRegistryUser;
    private String schemaRegistryPassword;
    private String schemaRegistryTrustStorePath;
    private String schemaRegistryTrustStorePassword;
    private String schemaRegistryKeyStorePath;
    private String schemaRegistryKeyStorePassword;
}
