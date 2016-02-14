package com.github.phillbarber.service.cassandra;

import com.github.phillbarber.external.ClusterFactory;
import com.github.phillbarber.external.DownstreamDBWithDummyValue;
import com.github.phillbarber.service.DummyDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DummyDAOTest {

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
        DummyDAO dummyDAO = new DummyDAO(new ClusterFactory().getCluster().newSession());
        assertThat(dummyDAO.getRowFromDB().toBlocking().first()).isEqualTo("Amazing Value");
    }


}

