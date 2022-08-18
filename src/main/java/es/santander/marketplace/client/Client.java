package es.santander.marketplace.client;

import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import es.santander.marketplace.client.filehandler.TopologyFileProcessor;

public class Client {
    public static void main(String[] args) throws StreamReadException, DatabindException, IOException {
        TopologyFileProcessor fileProcessor = new TopologyFileProcessor("/Users/ogomezsoriano/clients/Santander/code/marketplace-client/data-examples/topics.yaml","/Users/ogomezsoriano/clients/Santander/code/marketplace-client/data-examples/config.yaml");
        fileProcessor.processTopologyFile();
    }
}
