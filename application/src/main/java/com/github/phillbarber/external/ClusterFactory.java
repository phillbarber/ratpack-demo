package com.github.phillbarber.external;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.SocketOptions;

public class ClusterFactory {

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
