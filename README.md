# Santander Marketplace API Client

## Description

This client will generate json files from `julie-ops topology descriptor` in order to be capable to:

1. Register new events and its metadata
2. Register new subscription (producer or consumer) to a given topic

## Run the project

There is two main methods to build and execute the project:

### Uber jar file:

Since the whole project is package with maven you will need to package the project to create the jar file:

```bash
mvn clean package
```

and the execute the jar file:

```bash
java -jar target/marketplace-client-0.1.0-osx86_64.jar  <path to yaml config file> <path to topology yaml file>
```

> DISCLAIMER
> The Uber jar file is suffixed with the operative system info so the name will depends the host OS

### GraalVM Native Image

You also can package the entire client as a `graalVM native image` to do so:

```bash
mvn package -Pnative
```

to execute the binary generated:

```bash
target/marketplace-client  <path to yaml config file> <path to topology yaml file>
```
> DISCLAIMER
> The nature of the binary file depends on the host OS (sh executable file for Linux and osx and .exe file for windows) 

### Configuration

The client execution requires a configuration yaml file containing:

````
schemaRegistryUrl: Url Schema Registry (https)
schemaRegistryUser: Schema registry user (read permission needed)
schemaRegistryPassword: Password of Schema Registry User
schemaRegistryTrustStorePath: Path to the Schema registry truststore
schemaRegistryTrustStorePassword: Password for Schema Registry Truststore
schemaRegistryKeyStorePath: Path to Schema Registry Keystore
schemaRegistryKeyStorePassword: Password for Schema Registry Keystore
dumpEventsFilePath: Directory path to dump the event registration request Json file
dumpSubscriptionsFilePath: Directory path to dump the topic subscription request Json file
````

#### Example:

```config.yaml
schemaRegistryUrl: https://localhost:8085
schemaRegistryUser: superUser
schemaRegistryPassword: superUser
schemaRegistryTrustStorePath: data-examples/kafka.schemaregistry.truststore.jks
schemaRegistryTrustStorePassword: confluent
schemaRegistryKeyStorePath: data-examples/kafka.schemaregistry.keystore.jks
schemaRegistryKeyStorePassword: confluent
dumpEventsFilePath: data-examples/marketplace/events/
dumpSubscriptionsFilePath: data-examples/marketplace/subscriptions/
```

