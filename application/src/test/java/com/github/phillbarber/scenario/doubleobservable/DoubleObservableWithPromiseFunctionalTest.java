package com.github.phillbarber.scenario.doubleobservable;

import com.github.phillbarber.FunctionalTest;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleObservableWithPromiseFunctionalTest extends FunctionalTest {


    @Test
    @Ignore//TODO why does this approach work at work? i.e. why does an obervable that emits two items converted to a promise single work and NOT blow up with "TOO MANY ITEMS"
    //is it beacuse at work it calls .onComplete twice?
    public void returnsContent() throws URISyntaxException {

        URI uri = new URI(getAddress().toString() + "double-observable-promise");
        Response response = jerseyClient().target(uri).request().get();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(String.class)).isEqualTo("Downstream system returned: First Value");
    }
}
