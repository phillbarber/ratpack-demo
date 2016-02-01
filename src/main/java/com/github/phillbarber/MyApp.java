package com.github.phillbarber;

import com.github.phillbarber.happy.HappyPathHandler;
import com.github.phillbarber.happy.HappyPathService;
import io.netty.buffer.PooledByteBufAllocator;
import ratpack.http.client.internal.DefaultHttpClient;
import ratpack.server.RatpackServer;

public class MyApp {

    public static void main(String[] args) throws Exception {

//todo - understand the following line
        DefaultHttpClient httpClient = new DefaultHttpClient(PooledByteBufAllocator.DEFAULT, 100000);

        HappyPathService happyPathService = new HappyPathService(httpClient);

        RatpackServer.start(s -> s
                .handlers(chain -> {
                            chain
                                    .path("happy-path", new HappyPathHandler(happyPathService));
                        }
                )
        );
    }
}
