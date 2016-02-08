package com.github.phillbarber;

import com.github.phillbarber.scenario.doubleobservable.DoubleObservableHandler;
import com.github.phillbarber.scenario.doubleobservable.DoubleObservableHandlerWithPromise;
import com.github.phillbarber.scenario.doubleobservable.DoubleObservableService;
import com.github.phillbarber.scenario.happy.HappyHandler;
import com.github.phillbarber.scenario.happy.HappyService;
import com.github.phillbarber.scenario.observablethread.ObservableOnDifferentThreadHandlerBroken;
import com.github.phillbarber.scenario.observablethread.ObservableOnDifferentThreadHandlerFixed;
import com.github.phillbarber.scenario.observablethread.ObservableOnDifferentThreadService;
import io.netty.buffer.PooledByteBufAllocator;
import ratpack.http.client.internal.DefaultHttpClient;
import ratpack.server.RatpackServer;

public class MyApp {

    public static void main(String[] args) throws Exception {

//todo - understand the following line
        DefaultHttpClient httpClient = new DefaultHttpClient(PooledByteBufAllocator.DEFAULT, 100000);

        HappyService happyService = new HappyService(httpClient);

        RatpackServer.start(s -> s
                .handlers(chain -> {
                            chain
                                    .path("happy", new HappyHandler(happyService))
                                    .path("double-observable-promise", new DoubleObservableHandlerWithPromise(new DoubleObservableService()))
                                    .path("observable-different-thread-broken", new ObservableOnDifferentThreadHandlerBroken(new ObservableOnDifferentThreadService()))
                                    .path("observable-different-thread-fixed", new ObservableOnDifferentThreadHandlerFixed(new ObservableOnDifferentThreadService()))
                                    .path("double-observable", new DoubleObservableHandler(new DoubleObservableService()));
                        }
                )
        );
    }
}
