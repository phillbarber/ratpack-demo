package com.github.phillbarber.scenario.cassandra;

import com.github.phillbarber.ConcurrentExecutor;
import com.github.phillbarber.FunctionalTest;
import com.github.phillbarber.external.DownstreamDBWithDummyValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CassandraNonBlockingHotTest extends FunctionalTest {

    private static final int NUMBER_OF_CALLS = 8;
    private DownstreamDBWithDummyValue downstreamDBWithDummyValue = new DownstreamDBWithDummyValue();

    private Logger logger = LoggerFactory.getLogger(CassandraNonBlockingHotTest.class);

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
        Response response = getResponseFromHandler("cassandra-nonblocking-hot");
        verifyResponse(response);
    }

    @Test(timeout=DownstreamDBWithDummyValue.CASSANDRA_DELAY_IN_MILLIS * NUMBER_OF_CALLS)
    public void handlerIsNotBlocking() throws InterruptedException {

        List<Response> responses = new ConcurrentExecutor(() -> getResponseFromHandler("cassandra-nonblocking-hot"), NUMBER_OF_CALLS).executeRequestsInParallel();

        verifyAllResponses(responses, NUMBER_OF_CALLS);
    }

    private void verifyAllResponses(List<Response> responses, int numberOfCalls) {
        assertThat(responses.size()).isEqualTo(numberOfCalls);
        responses.forEach(response -> {
            verifyResponse(response);
        });
    }

    private void verifyResponse(Response response) {
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(String.class)).isEqualTo("DB Returned: Amazing Value");
    }
}
