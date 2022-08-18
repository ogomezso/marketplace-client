package es.santander.marketplace.client.model.topic;

import java.util.List;

import lombok.Data;

@Data
public class TopicTopology {
    private String context;
    private List<Project> projects; 
}
