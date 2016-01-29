import com.github.phillbarber.external.DownstreamService;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.github.phillbarber.MyApp;
import ratpack.test.MainClassApplicationUnderTest;
import ratpack.test.ServerBackedApplicationUnderTest;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.*;

public class HappyPathFunctionalTest {


    private DownstreamService downstreamService = new DownstreamService();

    @Before
    public void startDummyService() throws Exception {
        downstreamService.start();
    }

    @After
    public void stopDummyService(){
        downstreamService.stop();
    }


    @Test
    public void happyPathReturnsContent() throws URISyntaxException {
        ServerBackedApplicationUnderTest serverBackedApplicationUnderTest = new MainClassApplicationUnderTest(MyApp.class);

        URI baseURI = serverBackedApplicationUnderTest.getAddress();
        JerseyClient build = new JerseyClientBuilder().build();

        URI uri = new URI(baseURI.toString() + "happy-path");
        Response response = build.target(uri).request().get();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(String.class)).isEqualTo("Downstream system returned: YAY");


    }


}
