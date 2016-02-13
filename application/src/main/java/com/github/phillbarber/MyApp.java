package com.github.phillbarber;

import com.github.phillbarber.scenario.doubleobservable.DoubleObservableHandler;
import com.github.phillbarber.scenario.doubleobservable.DoubleObservableHandlerWithPromise;
import com.github.phillbarber.scenario.happy.HappyDeterministicHandler;
import com.github.phillbarber.scenario.happy.HappyHandler;
import com.github.phillbarber.scenario.happy.service.DownstreamHttpService;
import com.github.phillbarber.scenario.observablethread.ObservableOnDifferentThreadHandlerBroken;
import com.github.phillbarber.scenario.observablethread.ObservableOnDifferentThreadHandlerFixed;
import io.netty.buffer.PooledByteBufAllocator;
import ratpack.handling.internal.HeaderBasedRequestIdGenerator;
import ratpack.handling.internal.UuidBasedRequestIdGenerator;
import ratpack.http.client.internal.DefaultHttpClient;
import ratpack.logging.MDCInterceptor;
import ratpack.server.RatpackServer;

public class MyApp {

    public static final String REQUEST_ID = "Request-ID";

    public static void main(String[] args) throws Exception {

//todo - understand the following line
        DefaultHttpClient httpClient = new DefaultHttpClient(PooledByteBufAllocator.DEFAULT, 100000);

        DownstreamHttpService downstreamHttpService = new DownstreamHttpService(httpClient);

        RatpackServer.start(s -> s
                .registryOf( registrySpec -> registrySpec.add(new HeaderBasedRequestIdGenerator(REQUEST_ID, new UuidBasedRequestIdGenerator())))
                .handlers(chain -> {
                            chain
                                    .all(new RequestIDHandler())
                                    .path("happy", new HappyHandler(downstreamHttpService))
                                    .path("happy-deterministic", new HappyDeterministicHandler(downstreamHttpService))
                                    .path("double-observable-promise", new DoubleObservableHandlerWithPromise(new DoubleObservableService()))
                                    .path("observable-different-thread-broken", new ObservableOnDifferentThreadHandlerBroken(new ObservableOnDifferentThreadService()))
                                    .path("observable-different-thread-fixed", new ObservableOnDifferentThreadHandlerFixed(new ObservableOnDifferentThreadService()))
                                    .path("double-observable", new DoubleObservableHandler(new DoubleObservableService()));
                        }
                )
        );
    }
}
