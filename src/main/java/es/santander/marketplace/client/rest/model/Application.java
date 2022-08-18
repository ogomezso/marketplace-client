package es.santander.marketplace.client.rest.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Application {
    @Builder.Default
    private Integer appCodeId = 0;
    @Builder.Default
    private String appKey = "appkey";
    @Builder.Default
    private String appAtlas = "Atlas Name";
    @Builder.Default
    private String appDescription = "desc of app" ; 
    @Builder.Default
    private String appName = "app Name";
    @Builder.Default
    private Integer domainCodeId = 0;
    @Builder.Default
    private List<String> appOwners = List.of("developers@santander.com");
}
