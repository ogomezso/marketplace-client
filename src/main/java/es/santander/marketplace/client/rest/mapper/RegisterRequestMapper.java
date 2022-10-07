package es.santander.marketplace.client.rest.mapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import es.santander.marketplace.client.model.topic.Topic;
import es.santander.marketplace.client.rest.model.ApiTopic;
import es.santander.marketplace.client.rest.model.Application;
import es.santander.marketplace.client.rest.model.Event;
import es.santander.marketplace.client.rest.model.SchemaResponse;
import es.santander.marketplace.client.rest.model.TopicRegistration;

public class RegisterRequestMapper {

  public TopicRegistration toRequest(String projectName, Topic topic, SchemaResponse schema) {
    return TopicRegistration.builder().application(toApplication(projectName))
        .event(toEvent(topic, schema))
        .build();
  }

  private Application toApplication(String name) {
    return Application.builder().appKey(name).build();
  }

  private Event toEvent(Topic topic, SchemaResponse schema) {
    return Event.builder().eventName(topic.getMetadata().getEventName())
        .eventSchemaId(String.valueOf(schema.getId()))
        .eventSchemaVersion(String.valueOf(schema.getVersion()))
        .eventSchemaCompatibility(topic.getSchema().getCompatibility())
        .eventDescription(topic.getMetadata().getEventDescription()).topic(toApiTopic(topic))
        .build();
  }

  private ApiTopic toApiTopic(Topic topic) {
    return ApiTopic.builder().topicName(topic.getName())
        .topicDescription(topic.getMetadata().getTopicDescription()).
        topicCreationDate(getNowDateAsString()).topicType(
            mapTopicType(topic.getMetadata().getTopicType().toUpperCase()))
        .topicConfidentialityData(
            mapTopicConfidentialityData(
                topic.getMetadata().getTopicConfidentiality().toUpperCase()))
        .topicPartitions(getIntegerValueFromConfig(topic, "num.partitions").orElse(4))
        .topicTTL(
            getIntegerValueFromConfig(topic, "retention.ms").orElse(
                (int) Duration.ofHours(48).toMillis()))
        .topicPlatform(mapTopicPlatform(topic.getMetadata().getTopicPlatform().toUpperCase()))
        .topicStatus(
            convertTopicStatusToInteger(topic.getMetadata().getTopicStatus().toUpperCase()))
        .topicCDCsourceTable(topic.getMetadata().getTopicSourceTableCDC()).build();
  }

  private Integer mapTopicPlatform(String topicPlatform) {
    return switch (topicPlatform){
      case "AZURECLOUD" -> 1;
      case "AWS-CLOUD" -> 2;
      default -> 0;
    };
  }

  private Integer mapTopicConfidentialityData(String topicConfidentialityData) {
    return switch (topicConfidentialityData) {
      case "INTERNAL" -> 1;
      case "CONFIDENTIAL" -> 2;
      case "RESTRICTED-CONFIDENTIAL" -> 3;
      case "SECRET" -> 4;
      default -> 0;
    };
  }

  private Integer mapTopicType(String topicType) {
    return (topicType.equals("COMMAND")) ? 1 : 0;
  }

  private Optional<Integer> getIntegerValueFromConfig(Topic topic, String key) {
    return Optional.of((Integer) topic.getConfig().get(key));
  }

  private String getNowDateAsString() {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return LocalDateTime.now().format(format);
  }

  private Integer convertTopicStatusToInteger(String topicStatus) {
    return (topicStatus.equals("ACTIVE")) ? 1 : 0;
  }
}
