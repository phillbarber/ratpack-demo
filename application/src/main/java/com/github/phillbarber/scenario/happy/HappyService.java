package com.github.phillbarber.scenario.happy;

import ratpack.exec.Promise;
import ratpack.http.client.HttpClient;
import ratpack.http.client.ReceivedResponse;
import ratpack.rx.RxRatpack;
import rx.Observable;

import java.net.URI;
import java.net.URISyntaxException;

public class HappyService {

    private URI uri;
    private HttpClient httpClient;



    public HappyService(HttpClient httpClient) {
        this.httpClient = httpClient;
        try {
            uri = new URI("http://localhost:1234/fast-endpoint");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Observable<String> getContentFromDownstreamSystem(){
        Promise<ReceivedResponse> receivedResponsePromise = httpClient.get(uri);
        Observable<ReceivedResponse> observe = RxRatpack.observe(receivedResponsePromise);
        return observe.map(o -> o.getBody().getText());
    }

}
