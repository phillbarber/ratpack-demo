package com.github.phillbarber.scenario.doubleobservable;

import com.github.phillbarber.DoubleObservableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import rx.Observable;

import javax.inject.Singleton;

@Singleton
public class DoubleObservableHandler implements Handler {


    private Logger logger = LoggerFactory.getLogger(DoubleObservableHandler.class);


    private DoubleObservableService doubleObservableService;

    public DoubleObservableHandler(DoubleObservableService doubleObservableService) {
        this.doubleObservableService = doubleObservableService;
    }

    @Override
    public void handle(Context context) throws Exception {

        logger.info("Request received");

        Observable<String> contentFromDownstreamSystem = doubleObservableService.getContent();

        contentFromDownstreamSystem.subscribe(response -> {
                    context.render("Downstream system returned: " + response);
                }
        );
    }
}
