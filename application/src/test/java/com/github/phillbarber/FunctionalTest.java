package com.github.phillbarber;

import com.github.phillbarber.external.DownstreamService;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.After;
import org.junit.Before;
import ratpack.test.MainClassApplicationUnderTest;
import ratpack.test.ServerBackedApplicationUnderTest;

import java.net.URI;

public class FunctionalTest {

    private DownstreamService downstreamService = new DownstreamService();
    private ServerBackedApplicationUnderTest serverBackedApplicationUnderTest = new MainClassApplicationUnderTest(MyApp.class);

    public JerseyClient jerseyClient() {
        return new JerseyClientBuilder().build();
    }

    @Before
    public void startDummyService() throws Exception {
        downstreamService.start();
    }

    public URI getAddress() {
        return serverBackedApplicationUnderTest.getAddress();
    }


    @After
    public void stopDummyService(){
        downstreamService.stop();
    }



}
