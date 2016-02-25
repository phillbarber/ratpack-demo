package com.github.phillbarber.scenario.cassandra;

import com.github.phillbarber.ConcurrentExecutor;
import com.github.phillbarber.FunctionalTest;
import com.github.phillbarber.external.DownstreamDBWithDummyValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;

public class CassandraBlockingObviouslyTest extends FunctionalTest {

    private static final int NUMBER_OF_CALLS = 4;
    private DownstreamDBWithDummyValue downstreamDBWithDummyValue = new DownstreamDBWithDummyValue();

    private Logger logger = LoggerFactory.getLogger(CassandraBlockingObviouslyTest.class);

    @Before
    public void startDB() {
        downstreamDBWithDummyValue.start();
    }

    @After
    public void stopDB() {
        downstreamDBWithDummyValue.stop();
    }

    @Test
    public void returnsContent() throws URISyntaxException {
        Response response = getResponseFromHandler("cassandra-blocking-obviously");
        verifyDbHttpResponse(response);
    }

    @Test(timeout=DownstreamDBWithDummyValue.CASSANDRA_DELAY_IN_MILLIS * NUMBER_OF_CALLS)
    @Ignore("Because it is blocking!")
    public void handlerIsNotBlocking() throws InterruptedException {
        List<Response> responses = new ConcurrentExecutor(() -> getResponseFromHandler("cassandra-blocking-obviously"), NUMBER_OF_CALLS).executeRequestsInParallel();
        verifyAllDbHttpResponses(responses, NUMBER_OF_CALLS);
    }


}
