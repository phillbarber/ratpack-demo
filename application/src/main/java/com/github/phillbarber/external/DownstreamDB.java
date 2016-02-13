package com.github.phillbarber.external;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SocketOptions;
import org.scassandra.Scassandra;
import org.scassandra.ScassandraFactory;
import org.scassandra.http.client.PrimingClient;

public class DownstreamDB {

    private Cluster cluster;
    private PrimingClient primingClient;
    private Scassandra scassandra;

    public DownstreamDB(){

    }

    public void start() {
        scassandra = ScassandraFactory.createServer();
        scassandra.start();
        primingClient = scassandra.primingClient();


        SocketOptions socketOptions = new SocketOptions();
        socketOptions.setReadTimeoutMillis(5000);
        cluster = Cluster.builder()
                .addContactPoint("localhost")
                .withPort(8042)
                .withSocketOptions(socketOptions)
                .build();
    }

    public Session getSession() {
        return cluster.connect("scassandra");
    }

    public void stop(){
        scassandra.stop();
    }

    public PrimingClient getPrimingClient() {
        return primingClient;
    }

}
