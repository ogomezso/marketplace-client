package es.santander.marketplace.client.rest.mapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import es.santander.marketplace.client.model.topic.Topic;
import es.santander.marketplace.client.rest.model.ApiTopic;
import es.santander.marketplace.client.rest.model.Application;
import es.santander.marketplace.client.rest.model.Event;
import es.santander.marketplace.client.rest.model.EventDocumentation;
import es.santander.marketplace.client.rest.model.EventExample;
import es.santander.marketplace.client.rest.model.SchemaResponse;
import es.santander.marketplace.client.rest.model.TopicRegistration;

public class RegisterRequestMapper {
    public TopicRegistration toRequest(String projectName, Topic topic, SchemaResponse schema) {
        return TopicRegistration.builder()
                .application(toApplication(projectName))
                .event(toEvent(topic, schema))
                .eventDocumentation(EventDocumentation.builder().build())
                .eventExample(EventExample.builder().build())
                .build();
    }

    private Application toApplication(String name) {
        return Application.builder()
                .appKey(name)
                .build();
    }

    private Event toEvent(Topic topic, SchemaResponse schema) {
        return Event.builder()
                .eventName(topic.getMetadata().getEventName())
                .eventSchemaId(String.valueOf(schema.getId()))
                .eventSchemaVersion(String.valueOf(schema.getVersion()))
                .eventSchemaCompatibility(
                        topic.getSchema().getCompatibility())
                .eventDescription(topic.getMetadata().getEventDescription())
                .topic(toApiTopic(topic))
                .build();
    }

    private ApiTopic toApiTopic(Topic topic) {
        return ApiTopic.builder()
                .topicName(topic.getName())
                .topicDescription(topic.getMetadata().getTopicDescription())
                .topicCreationDate(getNowDateAsString())
                .topicType(topic.getMetadata().getTopicType())
                .topicConfidentialityData(
                        topic.getMetadata().getTopicConfidentiality().toUpperCase())
                .topicPartitions(String.valueOf(getIntegerValueFromConfig(topic, "num.partitions").orElse(4)))
                .topicTTL(getIntegerValueFromConfig(topic, "retention.ms").orElse((int) Duration.ofHours(48).toMillis()))
                .topicPlatform(topic.getMetadata().getTopicPlatform())
                .topicStatus(convertTopicStatusToInteger(topic.getMetadata().getTopicStatus()))
                .topicCDCsourceTable(
                        topic.getMetadata().getTopicSourceTableCDC())
                .build();
    }

    private Optional<Integer> getIntegerValueFromConfig(Topic topic, String key) {
        return Optional.of((Integer) topic.getConfig().get(key));
    }

    private String getNowDateAsString() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDateTime.now().format(format);
    }

    private Integer convertTopicStatusToInteger(String topicStatus) {
        return switch (topicStatus) {
            case "Active" -> 1;
            default -> 0;
        };
    }
}
