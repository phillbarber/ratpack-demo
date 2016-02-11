package com.github.phillbarber.scenario.observablethread;

import com.github.phillbarber.ObservableOnDifferentThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx.RxRatpack;
import rx.Observable;

import javax.inject.Singleton;

@Singleton
public class ObservableOnDifferentThreadHandlerFixed implements Handler {


    private Logger logger = LoggerFactory.getLogger(ObservableOnDifferentThreadHandlerFixed.class);


    private ObservableOnDifferentThreadService observableOnDifferentThreadService;

    public ObservableOnDifferentThreadHandlerFixed(ObservableOnDifferentThreadService observableOnDifferentThreadService) {
        this.observableOnDifferentThreadService = observableOnDifferentThreadService;
    }

    @Override
    public void handle(Context context) throws Exception {

        logger.info("Request received");

        Observable<String> contentFromDownstreamSystem = observableOnDifferentThreadService.getContent();

        RxRatpack.promise(contentFromDownstreamSystem).then(response -> {
                    context.render("Downstream system returned: " + response);
                }
        );
    }
}
