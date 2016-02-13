package com.github.phillbarber.service.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SocketOptions;
import com.github.phillbarber.external.cassandra.DummyDAO;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.scassandra.http.client.PrimingRequest;
import org.scassandra.junit.ScassandraServerRule;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.scassandra.http.client.PrimingRequest.then;

public class DummyDAOTest {


    @ClassRule
    public static final ScassandraServerRule SCASSANDRA = new ScassandraServerRule();
    private Cluster cluster;
    private Session session;



    @Before
    public void createClusterAndSession(){
        SocketOptions socketOptions = new SocketOptions();
        socketOptions.setReadTimeoutMillis(5000);
        cluster = Cluster.builder()
                .addContactPoint("localhost")
                .withPort(8042)
                .withSocketOptions(socketOptions)
                .build();
        session = cluster.connect("scassandra");
    }

    @Test
    public void shouldReturnRowFromDB(){

        Map<String, ?> row = ImmutableMap.of("dummy", "Amazing Value");

        SCASSANDRA.primingClient().prime(PrimingRequest.queryBuilder()
                .withQuery("select dummy from dummy")
                .withThen(then()
                        .withRows(row).withFixedDelay(1000l))
                .build());


        DummyDAO dummyDAO = new DummyDAO(session);
        assertThat(dummyDAO.getRowFromDB().toBlocking().first()).isEqualTo("Amazing Value");
    }


}

