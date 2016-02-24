package com.github.phillbarber.scenario.observablethread;

import ch.qos.logback.classic.Level;
import com.github.phillbarber.ExpectedLogs;
import com.github.phillbarber.FunctionalTest;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.phillbarber.scenario.observablethread.ObservableOnDifferentThreadBrokenTest.DOUBLE_TRANSMISSION_ERROR_MESASAGE;
import static com.github.phillbarber.scenario.observablethread.ObservableOnDifferentThreadBrokenTest.NO_RESPONSE_SENT_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

public class ObservableOnDifferentThreadFixedTest extends FunctionalTest {


    //https://github.com/ratpack/ratpack/issues/682


    @Rule
    public ExpectedLogs expectedLogs = ExpectedLogs.forRootLogger();


    @Test
    public void observableOnDifferentThreadWorksWhenConvertedToAPromise() throws URISyntaxException {
        URI uri = new URI(getAddress().toString() + "observable-different-thread-fixed");
        Response response = jerseyClient().target(uri).request().get();

        assertThat(response.getStatus()).isEqualTo(200);
        expectedLogs.assertLogsDoNotContain(Level.WARN, NO_RESPONSE_SENT_ERROR_MESSAGE);
        expectedLogs.assertLogsDoNotContain(Level.WARN, DOUBLE_TRANSMISSION_ERROR_MESASAGE);
    }

}
