package es.santander.marketplace.client;

import java.util.concurrent.Callable;

import es.santander.marketplace.client.filehandler.TopologyFileProcessor;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

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

    public static void main(String[] args) {

        int exitCode = new CommandLine(new Client()).execute(args);
       System.exit(exitCode);
    }
}
