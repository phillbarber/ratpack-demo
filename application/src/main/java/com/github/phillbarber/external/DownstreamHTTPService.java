package com.github.phillbarber.external;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class DownstreamHTTPService {

    private WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(1234));

    public void start() {
        wireMockServer.start();
        wireMockServer.stubFor(get(urlEqualTo("/fast-endpoint")).willReturn(aResponse().withStatus(200).withBody("YAY").withFixedDelay(100)));
        wireMockServer.stubFor(get(urlEqualTo("/slow-endpoint")).willReturn(aResponse().withStatus(200).withBody("YAY").withFixedDelay(3000)));
    }

    public void stop(){
        wireMockServer.stop();
    }

    public static void main(String[] args) {
        new DownstreamHTTPService().start();
    }

}
