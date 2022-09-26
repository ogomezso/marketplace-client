package es.santander.marketplace.client;

import java.io.IOException;
import java.util.concurrent.Callable;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import es.santander.marketplace.client.filehandler.TopologyFileProcessor;
import picocli.CommandLine.Parameters;
import picocli.CommandLine;

public class Client implements Callable<Integer> {
    @Parameters(index = "0", description = "Absolute path to client config file.")
    private String pathToConfigFile;

    @Parameters(index = "1", description = "Absolute path to Topology file to process.")
    private String pathToTopologyFile;

    @Override
    public Integer call() throws Exception {

        TopologyFileProcessor fileProcessor = new TopologyFileProcessor(
                pathToTopologyFile,
                pathToConfigFile);
        fileProcessor.processTopologyFile();
        return 0;
    }

    public static void main(String[] args) throws StreamReadException, DatabindException, IOException {

        int exitCode = new CommandLine(new Client()).execute(args);
        System.exit(exitCode);
    }
}
