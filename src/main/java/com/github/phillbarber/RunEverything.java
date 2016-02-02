package com.github.phillbarber;

import com.github.phillbarber.external.DownstreamService;

/**
 * Created by pergola on 29/01/16.
 */
public class RunEverything {

    public static void main(String[] args) throws Exception {
        MyApp.main(null);
        new DownstreamService().start();

    }

}
