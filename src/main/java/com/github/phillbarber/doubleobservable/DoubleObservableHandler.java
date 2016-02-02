package com.github.phillbarber.doubleobservable;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import rx.Observable;

import javax.inject.Singleton;

@Singleton
public class DoubleObservableHandler implements Handler {


    private DoubleObservableService doubleObservableService;

    public DoubleObservableHandler(DoubleObservableService doubleObservableService) {
        this.doubleObservableService = doubleObservableService;
    }

    @Override
    public void handle(Context context) throws Exception {

        Observable<String> contentFromDownstreamSystem = doubleObservableService.getContent();

        contentFromDownstreamSystem.subscribe(response -> {
                    context.render("Downstream system returned: " + response);  //Render the response from the httpClient GET request
                }
        );
    }
}
