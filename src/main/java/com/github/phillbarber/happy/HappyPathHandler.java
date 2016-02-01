package com.github.phillbarber.happy;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import rx.Observable;

import javax.inject.Singleton;

@Singleton
public class HappyPathHandler implements Handler {


    private HappyPathService happyPathService;

    public HappyPathHandler(HappyPathService happyPathService) {
        this.happyPathService = happyPathService;
    }

    @Override
    public void handle(Context context) throws Exception {

        Observable<String> contentFromDownstreamSystem = happyPathService.getContentFromDownstreamSystem();

        contentFromDownstreamSystem.subscribe(response -> {
                    context.render("Downstream system returned: " + response);  //Render the response from the httpClient GET request
                }
        );
    }
}
