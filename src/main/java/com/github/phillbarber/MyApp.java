package com.github.phillbarber;

import com.github.phillbarber.scenario.doubleobservable.DoubleObservableHandler;
import com.github.phillbarber.scenario.doubleobservable.DoubleObservableService;
import com.github.phillbarber.scenario.happy.HappyHandler;
import com.github.phillbarber.scenario.happy.HappyService;
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
                                    .path("double-observable", new DoubleObservableHandler(new DoubleObservableService()));
                        }
                )
        );
    }
}
