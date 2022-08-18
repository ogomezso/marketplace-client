package es.santander.marketplace.client.model.topic;

import lombok.Data;

@Data
public class Consumer {
   private String principal;
   private String group;
}
