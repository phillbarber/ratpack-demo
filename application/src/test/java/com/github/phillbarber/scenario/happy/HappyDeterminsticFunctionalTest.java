package com.github.phillbarber.scenario.happy;

import com.github.phillbarber.FunctionalTest;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class HappyDeterminsticFunctionalTest extends FunctionalTest {


    @Test
    public void happyPathReturnsContent() throws URISyntaxException {

        URI uri = new URI(getAddress().toString() + "happy-deterministic");
        Response response = jerseyClient().target(uri).request().get();

        assertThat(response.getStatus()).isEqualTo(500);
    }
}
