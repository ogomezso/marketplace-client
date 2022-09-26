package es.santander.marketplace.client.filehandler;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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

        String subject = topic.getName()+"-value";
        SchemaResponse schema = schemaRegistryClient.getSchemaInfo(appConfig, subject);
        TopicRegistration topicRegistration = registerRequestMapper.toRequest(projectName, topic, schema);

        List<Subscription> producerSubscriptions = topic.getProducers().stream()
                .map(producer -> subscriptionRequestMapper.toProducerSubscription(topic.getName(), producer))
                .collect(Collectors.toList());

        List<Subscription> consumerSubscriptions = topic.getConsumers().stream()
                .map(consumer -> subscriptionRequestMapper.toConsumerSubscription(topic.getName(), consumer)).toList();

        createJsonFile(topic.getName(), topicRegistration);

        producerSubscriptions.forEach(producer -> createJsonFile(
                producer.getSubscriptionType() + "_" + producer.getAppkey() + "_" + producer.getTopicName(), producer));
        consumerSubscriptions.forEach(consumer -> createJsonFile(consumer.getSubscriptionType() + "_"
                + consumer.getAppkey() + "_" + consumer.getTopicName() + "_" + consumer.getConsumerGroup(), consumer));
    }

    private void createJsonFile(String filename, Object pojo) {
        try {
            fileMapper.writeValue(Paths.get(filename + ".json").toFile(), pojo);
        } catch (IOException e) {
            System.out.println("Error processing registration file");
            e.printStackTrace();
        }
    }
}
