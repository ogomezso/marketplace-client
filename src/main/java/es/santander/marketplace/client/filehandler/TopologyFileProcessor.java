package es.santander.marketplace.client.filehandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.santander.marketplace.client.config.AppConfig;
import es.santander.marketplace.client.model.topic.Project;
import es.santander.marketplace.client.model.topic.Topic;
import es.santander.marketplace.client.model.topic.TopicTopology;
import es.santander.marketplace.client.rest.client.SchemaRegistryClient;
import es.santander.marketplace.client.rest.mapper.RegisterRequestMapper;
import es.santander.marketplace.client.rest.mapper.SubscriptionRequestMapper;
import es.santander.marketplace.client.rest.model.SchemaResponse;
import es.santander.marketplace.client.rest.model.Subscription;
import es.santander.marketplace.client.rest.model.TopicRegistration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TopologyFileProcessor {

    private final YamlToPojo yamlToPojo = new YamlToPojo();
    private final ObjectMapper fileMapper = new ObjectMapper();
    private final RegisterRequestMapper registerRequestMapper = new RegisterRequestMapper();
    private final SubscriptionRequestMapper subscriptionRequestMapper = new SubscriptionRequestMapper();

    private final SchemaRegistryClient schemaRegistryClient = new SchemaRegistryClient();

    private final AppConfig appConfig;
    private final TopicTopology topology;

    public TopologyFileProcessor(String topologyFilePath, String configFilepath)
            throws StreamReadException, DatabindException, IOException {

        this.appConfig = yamlToPojo.yamlToConfig(configFilepath);
        this.topology = yamlToPojo.yamlToTopic(topologyFilePath);
    }

    public void processTopologyFile()
            throws StreamReadException, DatabindException, IOException {

        topology.getProjects().forEach(project -> processProject(project));

    }

    private void processProject(Project project) {

        String projectName = project.getName();
        project.getTopics().forEach(topic -> processTopic(projectName, topic));

    }

    private void processTopic(String projectName, Topic topic) {
        String fullyQualifiedTopicName = "SANES."+ projectName + "." + topic.getName();
        String subject = fullyQualifiedTopicName + "-value";
        SchemaResponse schema = schemaRegistryClient.getSchemaInfo(appConfig, subject);
        TopicRegistration topicRegistration = registerRequestMapper.toRequest(projectName, topic, schema);
        topicRegistration.getEvent().getTopic().setTopicName(fullyQualifiedTopicName);

        createFolderIfNotExists(appConfig.getDumpEventsFilePath());
        createFolderIfNotExists(appConfig.getDumpSubscriptionsFilePath());

        List<Subscription> producerSubscriptions = topic.getProducers().stream()
                .map(producer -> subscriptionRequestMapper.toProducerSubscription(fullyQualifiedTopicName, producer))
                .collect(Collectors.toList());

        List<Subscription> consumerSubscriptions = topic.getConsumers().stream()
                .map(consumer -> subscriptionRequestMapper.toConsumerSubscription(fullyQualifiedTopicName, consumer)).toList();

        createJsonFile(appConfig.getDumpEventsFilePath() + fullyQualifiedTopicName, topicRegistration);
        createSubjectSchemaFilePathFile(appConfig.getDumpEventsFilePath() + subject+".txt", topic.getSchemas().getSchemaFile());

        producerSubscriptions.forEach(producer -> createJsonFile(
                appConfig.getDumpSubscriptionsFilePath() + "PRODUCER" + "_" + producer.getAppkey() + "_"
                        + producer.getTopicName(),
                producer));
        consumerSubscriptions.forEach(consumer -> createJsonFile(
                appConfig.getDumpSubscriptionsFilePath() + "CONSUMER" + "_"
                        + consumer.getAppkey() + "_" + consumer.getTopicName() + "_" + consumer.getSubtConsumerGroup(),
                consumer));
    }

    private void createJsonFile(String fileName, Object pojo) {
        createFile(fileName, ".json", pojo);
    }

    private void createSubjectSchemaFilePathFile(String filename, String path) {
        try {
            Files.write(Paths.get(filename), path.getBytes());
        } catch (IOException e) {
            System.out.println("Error processing Schema file");
            e.printStackTrace();
        }
    }

    private void createFile(String filename, String extension, Object pojo) {
        fileMapper.setSerializationInclusion(Include.NON_NULL);
        try {
            fileMapper.writeValue(Paths.get(filename + extension).toFile(), pojo);
        } catch (IOException e) {
            System.out.println("Error processing registration file");
            e.printStackTrace();
        }
    }

    private void createFolderIfNotExists(String path) {
        Path dir = Paths.get(path);
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            log.error("Error creating dump files path");
            e.printStackTrace();
        }
    }
}
