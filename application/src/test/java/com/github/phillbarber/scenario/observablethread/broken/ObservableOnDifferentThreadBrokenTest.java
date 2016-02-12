package com.github.phillbarber.scenario.observablethread.broken;

import ch.qos.logback.classic.Level;
import com.github.phillbarber.ExpectedLogs;
import com.github.phillbarber.FunctionalTest;
import org.assertj.core.api.AbstractIntegerAssert;
import org.junit.Rule;
import org.junit.Test;
//import ratpack.handling.internal.DoubleTransmissionException;
//import uk.org.lidalia.slf4jtest.LoggingEvent;
//import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservableOnDifferentThreadBrokenTest extends FunctionalTest {
    public static final String NO_RESPONSE_SENT_ERROR_MESSAGE = "No response sent for GET request";
    public static final String DOUBLE_TRANSMISSION_ERROR_MESASAGE = "attempt at double transmission";


    //https://github.com/ratpack/ratpack/issues/682

    @Rule
    public ExpectedLogs expectedLogs = ExpectedLogs.forRootLogger();

    @Test
    public void observableOnDifferentThreadGivesError() throws URISyntaxException {


        URI uri = new URI(getAddress().toString() + "observable-different-thread-broken");
        Response response = jerseyClient().target(uri).request().get();

        AbstractIntegerAssert<?> abstractIntegerAssert = assertThat(response.getStatus());
        abstractIntegerAssert.isEqualTo(500);

        //removing the following assertion, in some situations (e.g. when test run from gradle and not idea).
        //will result in the double transmission not occuring.
        //However, this test is non deterministic by its nature anyway.
        assertThat(response.readEntity(String.class)).contains(NO_RESPONSE_SENT_ERROR_MESSAGE);

        expectedLogs.assertLogsContain(Level.WARN, NO_RESPONSE_SENT_ERROR_MESSAGE);
        expectedLogs.assertLogsContain(Level.WARN, DOUBLE_TRANSMISSION_ERROR_MESASAGE);
    }
}
