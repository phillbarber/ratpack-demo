package com.github.phillbarber.scenario.happy;

import com.github.phillbarber.service.DownstreamHttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx.RxRatpack;
import rx.Observable;

public class HappyDeterministicHandler implements Handler {


    private Logger logger = LoggerFactory.getLogger(HappyDeterministicHandler.class);


    private DownstreamHttpService downstreamHttpService;

    public HappyDeterministicHandler(DownstreamHttpService downstreamHttpService) {
        this.downstreamHttpService = downstreamHttpService;
    }

    @Override
    public void handle(Context context) throws Exception {

        logger.info("Request received");

        Observable<String> contentFromDownstreamSystem = downstreamHttpService.getContentFromDownstreamSystem();

        RxRatpack.promiseSingle(contentFromDownstreamSystem).then(
                response -> context.render("Downstream system returned: " + response));

        //Thread.sleep(1000);
        context.getResponse().status(500).send();
    }
}
