package com.github.phillbarber;

import com.datastax.driver.core.Session;
import com.github.phillbarber.external.DownstreamDBWithDummyValue;
import com.github.phillbarber.scenario.blocking.BlockingHandler;
import com.github.phillbarber.scenario.cassandra.DAOHandler;
import com.github.phillbarber.scenario.doubleobservable.DoubleObservableHandler;
import com.github.phillbarber.scenario.doubleobservable.DoubleObservableHandlerWithPromise;
import com.github.phillbarber.scenario.happy.HappyDeterministicHandler;
import com.github.phillbarber.scenario.happy.HappyHandler;
import com.github.phillbarber.scenario.observablethread.ObservableOnDifferentThreadHandlerBroken;
import com.github.phillbarber.scenario.observablethread.ObservableOnDifferentThreadHandlerFixed;
import com.github.phillbarber.service.DownstreamHttpService;
import com.github.phillbarber.service.dao.BlockingObviouslyDAO;
import com.github.phillbarber.service.dao.BlockingOnSubscribeDAO;
import com.github.phillbarber.service.dao.NonBlockingColdDAO;
import com.github.phillbarber.service.dao.NonBlockingHotDAO;
import io.netty.buffer.PooledByteBufAllocator;
import ratpack.http.client.internal.DefaultHttpClient;
import ratpack.server.RatpackServer;

public class MyApp {


    public static void main(String[] args) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient(PooledByteBufAllocator.DEFAULT, 100000);

        DownstreamHttpService downstreamHttpService = new DownstreamHttpService(httpClient);

        Session session = new DownstreamDBWithDummyValue.ClusterFactory().getCluster().newSession();

        RatpackServer.start(s -> s
                .serverConfig(serverConfigBuilder -> {
                    serverConfigBuilder.port(8080);
                    serverConfigBuilder.threads(1);
                })
                .handlers(chain -> {

                    chain
                        .path("happy", new HappyHandler(downstreamHttpService))

                        .path("dao-blocking-obviously", new DAOHandler(new BlockingObviouslyDAO(session)))
                        .path("dao-blocking-subscribe", new DAOHandler(new BlockingOnSubscribeDAO(session)))
                        .path("dao-nonblocking-cold", new DAOHandler(new NonBlockingColdDAO(session)))
                        .path("dao-nonblocking-hot", new DAOHandler(new NonBlockingHotDAO(session)))

                        .path("double-observable-promise", new DoubleObservableHandlerWithPromise(new DoubleObservableService()))
                        .path("happy-deterministic", new HappyDeterministicHandler(downstreamHttpService))
                        .path("blocking", new BlockingHandler(downstreamHttpService))
                        .path("observable-different-thread-broken", new ObservableOnDifferentThreadHandlerBroken(new ObservableOnDifferentThreadService()))
                        .path("observable-different-thread-fixed", new ObservableOnDifferentThreadHandlerFixed(new ObservableOnDifferentThreadService()))
                        .path("double-observable", new DoubleObservableHandler(new DoubleObservableService()));
                        }
                )
        );
    }
}
