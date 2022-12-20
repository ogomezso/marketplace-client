package es.santander.marketplace.client.model.topic;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Topic {
    private String name;
    private Map<String,Object> config;
    private Schemas schemas;
    private List<Producer> producers;
    private List<Consumer> consumers;
    private Metadata metadata;
}
