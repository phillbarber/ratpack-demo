package com.github.phillbarber.scenario.blocking;

import com.github.phillbarber.service.DownstreamHttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx.RxRatpack;
import rx.Observable;

public class BlockingHandler implements Handler {


    private Logger logger = LoggerFactory.getLogger(BlockingHandler.class);


    private DownstreamHttpService happyPathService;

    public BlockingHandler(DownstreamHttpService downstreamHttpService) {
        this.happyPathService = downstreamHttpService;
    }

    @Override
    public void handle(Context context) throws Exception {

        logger.info("Request received");

        Observable<String> contentFromDownstreamSystem = happyPathService.getContentFromDownstreamSystem();

        Thread.sleep(1000);

        RxRatpack.promiseSingle(contentFromDownstreamSystem).then(
                response -> {
                    context.render("Downstream system returned: " + response);
                    logger.info("Response sent to client");
                });
    }
}
