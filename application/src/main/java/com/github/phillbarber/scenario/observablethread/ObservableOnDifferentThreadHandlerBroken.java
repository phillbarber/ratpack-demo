package com.github.phillbarber.scenario.observablethread;

import com.github.phillbarber.ObservableOnDifferentThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import rx.Observable;

import javax.inject.Singleton;

@Singleton
public class ObservableOnDifferentThreadHandlerBroken implements Handler {

    private Logger logger = LoggerFactory.getLogger(ObservableOnDifferentThreadHandlerBroken.class);

    private ObservableOnDifferentThreadService observableOnDifferentThreadService;

    public ObservableOnDifferentThreadHandlerBroken(ObservableOnDifferentThreadService observableOnDifferentThreadService) {
        this.observableOnDifferentThreadService = observableOnDifferentThreadService;
    }

    @Override
    public void handle(Context context) throws Exception {

        logger.info("Request received");

        Observable<String> contentFromDownstreamSystem = observableOnDifferentThreadService.getContent();

        contentFromDownstreamSystem.subscribe(response -> {
            context.render("Downstream system returned: " + response);
                }
        );
    }
}
