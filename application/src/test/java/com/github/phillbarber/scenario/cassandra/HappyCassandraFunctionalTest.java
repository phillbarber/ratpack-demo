package com.github.phillbarber.scenario.cassandra;

import com.github.phillbarber.FunctionalTest;
import com.github.phillbarber.external.DownstreamDBWithDummyValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class HappyCassandraFunctionalTest extends FunctionalTest {

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
    public void happyPathReturnsContent() throws URISyntaxException {

        URI uri = new URI(getAddress().toString() + "happy-cassandra");
        Response response = jerseyClient().target(uri).request().get();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(String.class)).isEqualTo("DB Returned: Amazing Value");
    }
}
