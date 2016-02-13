package com.github.phillbarber.scenario.blocking;

import com.github.phillbarber.service.DownstreamHttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.exec.Promise;
import ratpack.func.Action;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx.RxRatpack;
import rx.Observable;
import rx.observables.BlockingObservable;

public class BlockingHandler implements Handler {


    private Logger logger = LoggerFactory.getLogger(BlockingHandler.class);


    private DownstreamHttpService happyPathService;

    public BlockingHandler(DownstreamHttpService downstreamHttpService) {
        this.happyPathService = downstreamHttpService;
    }

    @Override
    public void handle(Context context) throws Exception {

        logger.info("Request received");


        Promise.of(o -> {}).then(new Action<Object>() {
            @Override
            public void execute(Object o) throws Exception {
                logger.info("WWWWAAAA");
                Observable<String> contentFromDownstreamSystem = happyPathService.getContentFromDownstreamSystem();
                BlockingObservable<String> stringBlockingObservable = contentFromDownstreamSystem.toBlocking();
                context.render("Downstream system returned: " + stringBlockingObservable.first());
            }
        });

        logger.info("Response sent to client");

    }
}
