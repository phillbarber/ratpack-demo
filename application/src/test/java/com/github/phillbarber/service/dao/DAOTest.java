package com.github.phillbarber.service.dao;

import com.datastax.driver.core.Session;
import com.github.phillbarber.external.DownstreamDBWithDummyValue;
import org.junit.After;
import org.junit.Before;

public abstract class DAOTest {

    private DownstreamDBWithDummyValue downstreamDBWithDummyValue = new DownstreamDBWithDummyValue();

    @Before
    public void startDB(){
        downstreamDBWithDummyValue.start();
    }

    @After
    public void stopDB(){
        downstreamDBWithDummyValue.stop();
    }


    public Session getSession() {
        return new DownstreamDBWithDummyValue.ClusterFactory().getCluster().newSession();
    }

}

