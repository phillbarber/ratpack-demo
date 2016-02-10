package com.github.phillbarber.scenario.observablethread;

import com.github.phillbarber.ObservableOnDifferentThreadService;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import rx.Observable;

import javax.inject.Singleton;

@Singleton
public class ObservableOnDifferentThreadHandlerBroken implements Handler {


    private ObservableOnDifferentThreadService observableOnDifferentThreadService;

    public ObservableOnDifferentThreadHandlerBroken(ObservableOnDifferentThreadService observableOnDifferentThreadService) {
        this.observableOnDifferentThreadService = observableOnDifferentThreadService;
    }

    @Override
    public void handle(Context context) throws Exception {

        Observable<String> contentFromDownstreamSystem = observableOnDifferentThreadService.getContent();

        contentFromDownstreamSystem.subscribe(response -> {
            context.render("Downstream system returned: " + response);
                }
        );
    }
}
