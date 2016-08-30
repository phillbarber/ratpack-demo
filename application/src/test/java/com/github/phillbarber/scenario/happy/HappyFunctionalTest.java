package com.github.phillbarber.scenario.happy;

import com.github.phillbarber.ConcurrentExecutor;
import com.github.phillbarber.FunctionalTest;
import com.github.phillbarber.external.DownstreamDBWithDummyValue;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HappyFunctionalTest extends FunctionalTest {


    public static final int NUMBER_OF_CALLS = 8;
    public static final String EXPECTED_RESPONSE = "Downstream system returned: YAY";

    @Test
    public void happyPathReturnsContent() throws URISyntaxException {
        Response response = getResponseFromHandler("happy");
        assertThat(response.getStatus()).isEqualTo(200);
        verifyResponse(response);
    }

    @Test(timeout = SLOW_ENDPOINT_DELAY * NUMBER_OF_CALLS)
    public void handlerIsNotBlocking() throws InterruptedException {
        List<Response> responses = new ConcurrentExecutor(() -> getResponseFromHandler("happy"), NUMBER_OF_CALLS)
                .executeRequestsInParallel();
        assertThat(responses).hasSize(NUMBER_OF_CALLS);
        responses.forEach(this::verifyResponse);

    }

    private void verifyResponse(Response response) {
        assertThat(response.readEntity(String.class)).isEqualTo(EXPECTED_RESPONSE);
    }
}
