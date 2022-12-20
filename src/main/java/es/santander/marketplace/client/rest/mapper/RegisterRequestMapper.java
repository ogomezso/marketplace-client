package es.santander.marketplace.client.rest.mapper;

import es.santander.marketplace.client.model.topic.Schemas;
import es.santander.marketplace.client.model.topic.Topic;
import es.santander.marketplace.client.rest.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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
        Schemas julieSchemaInfo = Optional.ofNullable(topic.getSchemas()).orElse(Schemas.builder().build());
        return Event.builder().eventName(topic.getMetadata().getEventName())
                .eventSchemaId(Optional.ofNullable(schema.getId()).orElse(0))
                .eventSchemaVersion(Optional.ofNullable(schema.getVersion()).orElse(0))
                .eventSchemaCompatibility(compatibilityToInt(Optional.ofNullable(julieSchemaInfo.getCompatibility()).orElse("")))
                .eventDescription(topic.getMetadata().getEventDescription()).topic(toApiTopic(topic,schema))
                .build();
    }

    private Integer compatibilityToInt(String compatibility) {
        return switch (compatibility.toUpperCase()) {
            case "BACKWARD" -> 0;
            case "BACKWARD_TRANSITIVE" -> 1;
            case "FORWARD" -> 2;
            case "FORWARD_TRANSITIVE" -> 3;
            case "FULL" -> 4;
            case "FULL_TRANSITIVE" -> 5;
            default -> 6;
        };
    }

    private ApiTopic toApiTopic(Topic topic, SchemaResponse schema) {
        return ApiTopic.builder()
                .topicName(topic.getName())
                .topicDescription(topic.getMetadata().getTopicDescription())
                .topicFormData(mapToFormData(Optional.ofNullable(schema.getSchemaType()).orElse("").toUpperCase()))
                .topicCreationDate(getNowDateAsString()).topicType(
                        mapTopicType(topic.getMetadata().getTopicType().toUpperCase()))
                .topicConfidentialityData(
                        mapTopicConfidentialityData(
                                topic.getMetadata().getTopicConfidentialityData().toUpperCase()))
                .topicPartitions(getIntegerValueFromConfig(topic, "num.partitions").orElse(4))
                .topicTTL(
                        getIntegerValueFromConfig(topic, "retention.ms").orElse(
                                (int) Duration.ofHours(48).toMillis()))
                .topicPlatform(mapTopicPlatform(topic.getMetadata().getTopicPlatform().toUpperCase()))
                .topicStatus(
                        convertTopicStatusToInteger(topic.getMetadata().getTopicStatus().toUpperCase()))
                .topicCDCsourceTable(topic.getMetadata().getTopicCDCSource()).build();
    }

    private Integer mapToFormData(String schemaType) {
        return switch (schemaType) {
            case "JSON" -> 0;
            case "AVRO" -> 1;
            default -> 2;
        };
    }

    private Integer mapTopicPlatform(String topicPlatform) {
        return switch (topicPlatform) {
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
