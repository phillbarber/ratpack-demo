package com.github.phillbarber.scenario.happy;

import com.github.phillbarber.service.DownstreamHttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.exec.Promise;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx.RxRatpack;
import rx.Observable;

public class HappyHandler implements Handler {


    private Logger logger = LoggerFactory.getLogger(HappyHandler.class);


    private DownstreamHttpService happyPathService;

    public HappyHandler(DownstreamHttpService downstreamHttpService) {
        this.happyPathService = downstreamHttpService;
    }

    @Override
    public void handle(Context context) throws Exception {

        logger.info("Request received");

        Observable<String> contentFromDownstreamSystem = happyPathService.getContentFromDownstreamSystem();

        Promise<String> stringPromise = RxRatpack.promiseSingle(contentFromDownstreamSystem);
        stringPromise.then(
                responseFromDownStreamSystem -> {
                    context.render("Downstream system returned: " + responseFromDownStreamSystem);
                    logger.info("Response sent to client");
                });
    }
}
