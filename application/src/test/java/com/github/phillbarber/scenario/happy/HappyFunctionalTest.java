package com.github.phillbarber.scenario.happy;

import com.github.phillbarber.FunctionalTest;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class HappyFunctionalTest extends FunctionalTest {


    @Test
    public void happyPathReturnsContent() throws URISyntaxException {

        URI uri = new URI(getAddress().toString() + "happy");
        Response response = jerseyClient().target(uri).request().get();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(String.class)).isEqualTo("Downstream system returned: YAY");
    }
}
