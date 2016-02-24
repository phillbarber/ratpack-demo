package com.github.phillbarber.service.cassandra;

import com.github.phillbarber.external.DownstreamDBWithDummyValue;
import com.github.phillbarber.service.BlockingCassandraDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockingCassandraDAOTest {

    private DownstreamDBWithDummyValue downstreamDBWithDummyValue = new DownstreamDBWithDummyValue();

    @Before
    public void startDB(){
        downstreamDBWithDummyValue.start();
    }

    @After
    public void stopDB(){
        downstreamDBWithDummyValue.stop();
    }

    @Test
    public void shouldReturnRowFromDB(){
        BlockingCassandraDAO blockingCassandraDAO = new BlockingCassandraDAO(new DownstreamDBWithDummyValue.ClusterFactory().getCluster().newSession());
        assertThat(blockingCassandraDAO.getRowFromDB().toBlocking().first()).isEqualTo("Amazing Value");
    }


}

