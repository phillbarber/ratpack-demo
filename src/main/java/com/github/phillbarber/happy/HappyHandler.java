package com.github.phillbarber.happy;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import rx.Observable;

import javax.inject.Singleton;

@Singleton
public class HappyHandler implements Handler {


    private HappyService happyPathService;

    public HappyHandler(HappyService happyPathService) {
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
