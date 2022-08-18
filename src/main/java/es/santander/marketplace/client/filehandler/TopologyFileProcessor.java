package es.santander.marketplace.client.filehandler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import es.santander.marketplace.client.config.AppConfig;
import es.santander.marketplace.client.model.topic.Project;
import es.santander.marketplace.client.model.topic.Topic;
import es.santander.marketplace.client.model.topic.TopicTopology;
import es.santander.marketplace.client.rest.client.RegistrationClient;
import es.santander.marketplace.client.rest.client.SchemaRegistryClient;
import es.santander.marketplace.client.rest.client.SubscriptionClient;
import es.santander.marketplace.client.rest.mapper.RegisterRequestMapper;
import es.santander.marketplace.client.rest.mapper.SubscriptionRequestMapper;
import es.santander.marketplace.client.rest.model.SchemaResponse;
import es.santander.marketplace.client.rest.model.Subscription;
import es.santander.marketplace.client.rest.model.TopicRegistration;
import jakarta.ws.rs.core.Response;

public class TopologyFileProcessor {

    private final YamlToPojo yamlToPojo = new YamlToPojo();
    private final RegisterRequestMapper registerRequestMapper = new RegisterRequestMapper();
    private final SubscriptionRequestMapper subscriptionRequestMapper = new SubscriptionRequestMapper();

    private final RegistrationClient registrationClient = new RegistrationClient();
    private final SubscriptionClient subscriptionClient = new SubscriptionClient();
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

        SchemaResponse schema = schemaRegistryClient.getSchemaInfo(appConfig, topic.getName());
        System.out.println(schema);
        TopicRegistration topicRegistration = registerRequestMapper.toRequest(projectName, topic, schema);
        System.out.println(topicRegistration);

        List<Subscription> subscriptions = topic.getProducers().stream()
                .map(producer -> subscriptionRequestMapper.toProducerSubscription(topic.getName(), producer))
                .collect(Collectors.toList());

        List<Subscription> consumerSubscriptions = topic.getConsumers().stream()
                .map(consumer -> subscriptionRequestMapper.toConsumerSubscription(topic.getName(), consumer)).toList();

        subscriptions.addAll(consumerSubscriptions);

        Response topicRegistrationResponse = registrationClient.registerNewTopic(topicRegistration, appConfig);

        Integer responseStatus = topicRegistrationResponse.getStatus();

        if (responseStatus >= 200 && responseStatus <= 299) {
            System.out.println("topic registered");
        } else {
            System.out.println("Topic Registration Error: " + responseStatus);
        }

        subscriptions.forEach(subscription -> {
            Response subsResponse = subscriptionClient.createNewTopicSubscription(subscription, appConfig);
            Integer subsResponseStatus = subsResponse.getStatus();

            if (subsResponseStatus >= 200 && subsResponseStatus <= 299) {
                System.out.println("Subscription Created");
            } else {
                System.out.println("Subs error: " + subsResponseStatus);
            }
        });
    }
}
