package com.github.phillbarber.scenario.happy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx.RxRatpack;
import rx.Observable;

public class HappyHandler implements Handler {


    private Logger logger = LoggerFactory.getLogger(HappyHandler.class);


    private HappyService happyPathService;

    public HappyHandler(HappyService happyPathService) {
        this.happyPathService = happyPathService;
    }

    @Override
    public void handle(Context context) throws Exception {

        logger.info("Request received");

        Observable<String> contentFromDownstreamSystem = happyPathService.getContentFromDownstreamSystem();

        RxRatpack.promiseSingle(contentFromDownstreamSystem).then(response -> {
                    context.render("Downstream system returned: " + response);
            }
        );
    }
}
