package com.github.phillbarber.service.cassandra;

import com.github.phillbarber.external.DownstreamDB;
import com.github.phillbarber.service.DummyDAO;
import com.google.common.collect.ImmutableMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scassandra.http.client.PrimingRequest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.scassandra.http.client.PrimingRequest.then;

public class DummyDAOTest {

    private DownstreamDB downstreamDB = new DownstreamDB();

    @Before
    public void startDB(){
        downstreamDB.start();
    }

    @After
    public void stopDB(){
        downstreamDB.stop();
    }


    private void primeWithDummyValue() {
        Map<String, ?> row = ImmutableMap.of("dummy", "Amazing Value");
        downstreamDB.getPrimingClient().prime(PrimingRequest.queryBuilder()
                .withQuery("select dummy from dummy")
                .withThen(then()
                        .withRows(row).withFixedDelay(1000l))
                .build());
    }

    @Test
    public void shouldReturnRowFromDB(){

        primeWithDummyValue();


        DummyDAO dummyDAO = new DummyDAO(downstreamDB.getSession());
        assertThat(dummyDAO.getRowFromDB().toBlocking().first()).isEqualTo("Amazing Value");
    }


}

