package com.github.phillbarber.scenario.cassandra;

import com.github.phillbarber.FunctionalTest;
import com.github.phillbarber.external.DownstreamDBWithDummyValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockingCassandraFunctionalTest extends FunctionalTest {

    private DownstreamDBWithDummyValue downstreamDBWithDummyValue = new DownstreamDBWithDummyValue();

    private Logger logger = LoggerFactory.getLogger(BlockingCassandraFunctionalTest.class);

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

        Response response = getResponseFromHandler();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(String.class)).isEqualTo("DB Returned: Amazing Value");
    }

    private Response getResponseFromHandler() throws URISyntaxException {
        URI uri = new URI(getAddress().toString() + "blocking-cassandra");
        Response response = jerseyClient().target(uri).request().get();
        logger.info("DoneDone: {}", response);
        return response;
    }

    @Test(timeout = DownstreamDBWithDummyValue.CASSANDRA_DELAY_IN_MILLIS * 2)
    @Ignore("This test will fail since the call is blocking")
    public void handlerIsBlocking() throws InterruptedException {

        List<Callable<Response>> twoCallsToHandler = Arrays.asList(this::getResponseFromHandler, this::getResponseFromHandler);

        List<Response> responses = executeTasksInParallel(twoCallsToHandler);

        assertThat(responses.size()).isEqualTo(twoCallsToHandler.size());
        responses.forEach(response -> {
            assertThat(response.getStatus()).isEqualTo(200);
        });


    }

    private List<Response> executeTasksInParallel(List<Callable<Response>> tasks) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(tasks.size());
        List<Response> responses = executorService.invokeAll(tasks).parallelStream().map(responseFuture -> {
            try {
                return responseFuture.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        executorService.shutdownNow();
        return responses;
    }
}
