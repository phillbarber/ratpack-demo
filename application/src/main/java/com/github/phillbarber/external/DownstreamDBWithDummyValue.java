package com.github.phillbarber.external;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.SocketOptions;
import com.google.common.collect.ImmutableMap;
import org.scassandra.Scassandra;
import org.scassandra.ScassandraFactory;
import org.scassandra.http.client.PrimingClient;
import org.scassandra.http.client.PrimingRequest;

import java.util.Map;

import static org.scassandra.http.client.PrimingRequest.then;

public class DownstreamDBWithDummyValue {

    private PrimingClient primingClient;
    private Scassandra scassandra;

    public DownstreamDBWithDummyValue(){

    }

    public void start() {
        scassandra = ScassandraFactory.createServer();
        scassandra.start();
        primingClient = scassandra.primingClient();
        primeWithDummyValue();
    }

    private void primeWithDummyValue() {
        Map<String, ?> row = ImmutableMap.of("dummy", "Amazing Value");
        primingClient.prime(PrimingRequest.queryBuilder()
                .withQuery("select dummy from dummy")
                .withThen(then()
                        .withRows(row))//.withFixedDelay(1000l))
                .build());
    }


    public void stop(){
        scassandra.stop();
    }

    public PrimingClient getPrimingClient() {
        return primingClient;
    }

    public static void main(String[] args) {
        new DownstreamDBWithDummyValue().start();
    }

    public static class ClusterFactory {

        public Cluster getCluster() {
            SocketOptions socketOptions = new SocketOptions();
            socketOptions.setReadTimeoutMillis(5000);
            return Cluster.builder()
                    .addContactPoint("localhost")
                    .withPort(8042)
                    .withSocketOptions(socketOptions)
                    .build();
        }
    }
}
