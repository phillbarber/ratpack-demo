package com.github.phillbarber;

import ratpack.server.RatpackServer;

public class MyApp {

  public static void main(String[] args) throws Exception {


    RatpackServer.start(s -> s
        .handlers(chain -> chain
            .path("happy-path", new HappyPathHandler())
        )
    );
  }
}