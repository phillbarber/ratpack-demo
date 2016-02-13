package com.github.phillbarber.service.http;

import com.github.phillbarber.service.DownstreamHttpService;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.netty.buffer.PooledByteBufAllocator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import ratpack.exec.ExecResult;
import ratpack.http.client.internal.DefaultHttpClient;
import ratpack.test.exec.ExecHarness;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static ratpack.rx.RxRatpack.promiseSingle;

public class DownstreamHttpServiceTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(1234));

    @Before
    public void setup(){
        wireMockRule.stubFor(get(urlEqualTo("/fast-endpoint")).willReturn(aResponse().withStatus(200).withBody("YAY").withFixedDelay(100)));
    }

    @Test
    public void happyPathHandlerReturnsYAY() throws Exception {
        DownstreamHttpService underTest = new DownstreamHttpService(new DefaultHttpClient(PooledByteBufAllocator.DEFAULT, 100000));
        assertThat(callServiceInRatpackThread(underTest)).isEqualTo("YAY");
    }

    private String callServiceInRatpackThread(DownstreamHttpService underTest) throws Exception {
        try (ExecHarness harness = ExecHarness.harness()) {
            ExecResult<String> yield = harness.yield(execution -> promiseSingle(underTest.getContentFromDownstreamSystem()));
            return yield.getValueOrThrow();
        }
    }

}
