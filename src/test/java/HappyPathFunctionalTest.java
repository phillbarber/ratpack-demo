import external.DummyService;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ratpack.example.java.MyApp;
import ratpack.test.MainClassApplicationUnderTest;
import ratpack.test.ServerBackedApplicationUnderTest;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class HappyPathFunctionalTest {


    private DummyService dummyService = new DummyService();

    @Before
    public void startDummyService() throws Exception {
        dummyService.start();
    }

    @After
    public void stopDummyService(){
        dummyService.stop();
    }


    @Test
    public void happyPathReturnsContent() throws URISyntaxException {
        ServerBackedApplicationUnderTest serverBackedApplicationUnderTest = new MainClassApplicationUnderTest(MyApp.class);

        URI baseURI = serverBackedApplicationUnderTest.getAddress();
        JerseyClient build = new JerseyClientBuilder().build();

        Response response = build.target(new URI(baseURI.toString() + "happy-path")).request().get();

        assertEquals(response.getStatus(), 200);


    }


}
