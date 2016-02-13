package com.github.phillbarber.scenario.blocking;

import com.github.phillbarber.FunctionalTest;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

public class BlockingFunctionalTest extends FunctionalTest {




    @Test
    @Ignore("Not working")
    public void happyPathReturnsContent() throws URISyntaxException {

        URI uri = new URI(getAddress().toString() + "blocking");
        Response response = jerseyClient().target(uri).request().get();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(String.class)).isEqualTo("Downstream system returned: YAY");
    }
}
