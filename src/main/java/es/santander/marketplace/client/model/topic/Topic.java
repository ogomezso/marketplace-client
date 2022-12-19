package es.santander.marketplace.client.model.topic;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Topic {
    private String name;
    private Map<String,Object> config;
    private Schema schemas;
    private List<Producer> producers;
    private List<Consumer> consumers;
    private Metadata metadata;
}
