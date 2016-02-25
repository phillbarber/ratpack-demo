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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;

public class CassandraNonBlockingHotTest extends FunctionalTest {

    public static final String CASSANDRA_BLOCKING_OBVIOUSLY_URI = "cassandra-blocking-subscribe";
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
        Response response = getResponseFromHandler(CASSANDRA_BLOCKING_OBVIOUSLY_URI);
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(String.class)).isEqualTo("DB Returned: Amazing Value");
    }

    @Test(timeout=DownstreamDBWithDummyValue.CASSANDRA_DELAY_IN_MILLIS * NUMBER_OF_CALLS)
    public void handlerIsNotBlocking() throws InterruptedException {

        List<Response> responses = new ConcurrentExecutor(() -> getResponseFromHandler(CASSANDRA_BLOCKING_OBVIOUSLY_URI), NUMBER_OF_CALLS).executeRequestsInParallel();

        assertThat(responses.size()).isEqualTo(NUMBER_OF_CALLS);
        responses.forEach(response -> {
            assertThat(response.getStatus()).isEqualTo(200);
            assertThat(response.readEntity(String.class)).isEqualTo("DB Returned: Amazing Value");
        });
    }
}
