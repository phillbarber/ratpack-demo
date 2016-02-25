package com.github.phillbarber;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.test.MainClassApplicationUnderTest;
import ratpack.test.ServerBackedApplicationUnderTest;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class FunctionalTest {

    private Logger logger = LoggerFactory.getLogger(FunctionalTest.class);


    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(1234));

    private ServerBackedApplicationUnderTest serverBackedApplicationUnderTest = new MainClassApplicationUnderTest(MyApp.class);



    @Before
    public void setup(){
        wireMockRule.stubFor(get(urlEqualTo("/fast-endpoint")).willReturn(aResponse().withStatus(200).withBody("YAY").withFixedDelay(100)));
        wireMockRule.stubFor(get(urlEqualTo("/slow-endpoint")).willReturn(aResponse().withStatus(200).withBody("YAY").withFixedDelay(3000)));
        //call this method first to ensure the application is started.
        getAddress();
    }

    public JerseyClient jerseyClient() {
        ClientConfig config = new ClientConfig();
        config.property(ClientProperties.READ_TIMEOUT, 6000);
        return new JerseyClientBuilder().withConfig(config).build();
    }

    public Response getResponseFromHandler(String path)  {
        URI uri = null;
        try {
            uri = new URI(getAddress().toString() + path);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Response response = jerseyClient().target(uri).request().get();
        logger.info("DoneDone: {}", response);
        return response;
    }


    public URI getAddress() {
        return serverBackedApplicationUnderTest.getAddress();
    }

}
