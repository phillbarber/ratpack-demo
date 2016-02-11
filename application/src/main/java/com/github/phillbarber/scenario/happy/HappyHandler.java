package com.github.phillbarber.scenario.happy;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx.RxRatpack;
import rx.Observable;

public class HappyHandler implements Handler {


    private HappyService happyPathService;

    public HappyHandler(HappyService happyPathService) {
        this.happyPathService = happyPathService;
    }

    @Override
    public void handle(Context context) throws Exception {

        Observable<String> contentFromDownstreamSystem = happyPathService.getContentFromDownstreamSystem();

        RxRatpack.promiseSingle(contentFromDownstreamSystem).then(response -> {
                    context.render("Downstream system returned: " + response);
            }
        );
    }
}
