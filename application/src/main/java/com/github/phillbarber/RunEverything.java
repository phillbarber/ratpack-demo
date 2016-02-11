package com.github.phillbarber;

import com.github.phillbarber.external.DownstreamService;

public class RunEverything {

    public static void main(String[] args) throws Exception {
        MyApp.main(null);
        new DownstreamService().start();

    }

}
