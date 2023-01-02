package es.santander.marketplace.client.filehandler;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import es.santander.marketplace.client.config.AppConfig;
import es.santander.marketplace.client.model.topic.TopicTopology;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public class YamlToPojo {

    private final YAMLMapper yamlMapper = new YAMLMapper();

    public TopicTopology yamlToTopic(String topologyFilePath)
            throws IOException {
        File configFile = new File(topologyFilePath);
        return yamlMapper.readValue(configFile, TopicTopology.class);
    }

    public AppConfig yamlToConfig(String configFilePath) throws IOException {
        File configFile = new File(configFilePath);
        return yamlMapper.readValue(configFile, AppConfig.class);
    }
}
