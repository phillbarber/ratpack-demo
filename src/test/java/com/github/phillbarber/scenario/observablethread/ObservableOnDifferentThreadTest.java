package com.github.phillbarber.scenario.observablethread;

import com.github.phillbarber.FunctionalTest;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservableOnDifferentThreadTest extends FunctionalTest {


    //https://github.com/ratpack/ratpack/issues/682

    @Test
    public void observableOnDifferentThreadGivesError() throws URISyntaxException {


        URI uri = new URI(getAddress().toString() + "observable-different-thread");
        Response response = jerseyClient().target(uri).request().get();

        assertThat(response.getStatus()).isEqualTo(500);

        //todo check that two errors are emitted to the logs... -Noresponse AND double response
    }
}
