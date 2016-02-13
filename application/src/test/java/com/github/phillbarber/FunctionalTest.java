package com.github.phillbarber;

import com.github.phillbarber.external.DownstreamService;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import ratpack.test.MainClassApplicationUnderTest;
import ratpack.test.ServerBackedApplicationUnderTest;

import javax.ws.rs.core.Configuration;
import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class FunctionalTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(1234));

    @Before
    public void setup(){
        wireMockRule.stubFor(get(urlEqualTo("/fast-endpoint")).willReturn(aResponse().withStatus(200).withBody("YAY").withFixedDelay(100)));
        wireMockRule.stubFor(get(urlEqualTo("/slow-endpoint")).willReturn(aResponse().withStatus(200).withBody("YAY").withFixedDelay(3000)));
    }

    private ServerBackedApplicationUnderTest serverBackedApplicationUnderTest = new MainClassApplicationUnderTest(MyApp.class);

    public JerseyClient jerseyClient() {
        ClientConfig config = new ClientConfig();
        config.property(ClientProperties.READ_TIMEOUT, 6000);
        return new JerseyClientBuilder().withConfig(config).build();
    }


    public URI getAddress() {
        return serverBackedApplicationUnderTest.getAddress();
    }

}
