package es.santander.marketplace.client.rest.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Application {
    private Integer appCodeId;
    private String appKey;
    private String appName;
    private String appDescription;
    private Integer domainCodeId;
}
