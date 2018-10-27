package uk.co.mruoc.repository;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.rules.ExternalResource;

public class LocalDynamoRule extends ExternalResource {

    private static final int DEFAULT_PORT = 8000;

    private final int port;
    private DynamoDBProxyServer server;

    public LocalDynamoRule() {
        this(DEFAULT_PORT);
    }

    public LocalDynamoRule(int port) {
        this.port = port;
    }

    @Override
    protected void before() throws Exception {
        this.server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory", "-port", Integer.toString(port)});
        server.start();
    }

    @Override
    protected void after() {
        this.stopUnchecked(server);
    }

    private void stopUnchecked(DynamoDBProxyServer dynamoDbServer) {
        try {
            dynamoDbServer.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}