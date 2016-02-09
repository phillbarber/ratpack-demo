package com.github.phillbarber.external;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class DownstreamService {

    public static final int FIVE_SECONDS = 5000;
    public static final int ONE_HUNDRED_MILLIS = 100;
    private WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(1234));

    public void start() {
        wireMockServer.start();
        wireMockServer.stubFor(get(urlEqualTo("/fast-endpoint")).willReturn(aResponse().withStatus(200).withBody("YAY").withFixedDelay(ONE_HUNDRED_MILLIS)));
        wireMockServer.stubFor(get(urlEqualTo("/slow-endpoint")).willReturn(aResponse().withStatus(200).withBody("YAY").withFixedDelay(FIVE_SECONDS)));
    }

    public void stop(){
        wireMockServer.stop();
    }

}
