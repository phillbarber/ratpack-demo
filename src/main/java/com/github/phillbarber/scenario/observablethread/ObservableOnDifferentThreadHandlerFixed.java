package com.github.phillbarber.scenario.observablethread;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx.RxRatpack;
import rx.Observable;

import javax.inject.Singleton;

@Singleton
public class ObservableOnDifferentThreadHandlerFixed implements Handler {


    private ObservableOnDifferentThreadService observableOnDifferentThreadService;

    public ObservableOnDifferentThreadHandlerFixed(ObservableOnDifferentThreadService observableOnDifferentThreadService) {
        this.observableOnDifferentThreadService = observableOnDifferentThreadService;
    }

    @Override
    public void handle(Context context) throws Exception {

        Observable<String> contentFromDownstreamSystem = observableOnDifferentThreadService.getContent();

        RxRatpack.promise(contentFromDownstreamSystem).then(response -> {
                    context.render("Downstream system returned: " + response);
                }
        );
    }
}
