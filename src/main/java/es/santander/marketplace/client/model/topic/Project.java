package es.santander.marketplace.client.model.topic;

import java.util.List;

import lombok.Data;

@Data
public class Project {
    private String name;
    private List<Topic> topics;
}
