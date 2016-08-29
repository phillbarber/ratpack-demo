package com.github.phillbarber.service.dao;

import com.datastax.driver.core.Session;
import com.github.phillbarber.external.DownstreamDBWithDummyValue;
import org.junit.After;
import org.junit.Before;
import ratpack.exec.ExecResult;
import ratpack.test.exec.ExecHarness;
import rx.Observable;

import java.util.function.Supplier;

import static ratpack.rx.RxRatpack.promiseSingle;

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

    public String callServiceInRatpackThread(Supplier<Observable<String>> s) throws Exception {

        try (ExecHarness harness = ExecHarness.harness()) {
            ExecResult<String> yield = harness.yield(execution -> promiseSingle(s.get()));
            return yield.getValueOrThrow();
        }
    }
}

