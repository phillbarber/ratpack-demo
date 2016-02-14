package com.github.phillbarber.external;

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
                        .withRows(row).withFixedDelay(1000l))
                .build());
    }


    public void stop(){
        scassandra.stop();
    }

    public PrimingClient getPrimingClient() {
        return primingClient;
    }

}
