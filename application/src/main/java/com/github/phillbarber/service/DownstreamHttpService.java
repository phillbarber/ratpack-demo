package com.github.phillbarber.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.exec.Promise;
import ratpack.http.client.HttpClient;
import ratpack.http.client.ReceivedResponse;
import ratpack.rx.RxRatpack;
import rx.Observable;

import java.net.URI;
import java.net.URISyntaxException;

public class DownstreamHttpService {

    private URI uri;
    private HttpClient httpClient;
    private Logger logger = LoggerFactory.getLogger(DownstreamHttpService.class);



    public DownstreamHttpService(HttpClient httpClient) {
        this.httpClient = httpClient;
        try {
            uri = new URI("http://localhost:1234/slow-endpoint");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Observable<String> getContentFromDownstreamSystem(){
        Promise<ReceivedResponse> receivedResponsePromise = httpClient.get(uri);

        Observable<ReceivedResponse> observe = RxRatpack.observe(receivedResponsePromise);
        return observe.map(receivedResponse -> {
            logger.info("Got response from downstream.");
            return receivedResponse.getBody().getText();
        });
    }

}
