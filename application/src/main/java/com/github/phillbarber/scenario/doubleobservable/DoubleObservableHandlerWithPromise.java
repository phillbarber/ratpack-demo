package com.github.phillbarber.scenario.doubleobservable;

import com.github.phillbarber.DoubleObservableService;
import ratpack.exec.Promise;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx.RxRatpack;
import rx.Observable;

public class DoubleObservableHandlerWithPromise implements Handler {

    private DoubleObservableService doubleObservableService;

    public DoubleObservableHandlerWithPromise (DoubleObservableService doubleObservableService) {
        this.doubleObservableService = doubleObservableService;
    }

    @Override
    public void handle(Context context) throws Exception {

        Observable<String> contentFromDownstreamSystem = doubleObservableService.getContent();

        Promise<String> promise = RxRatpack.promiseSingle(contentFromDownstreamSystem);


        promise.then(content -> context.render("Downstream system returned: " + content));

    }
}
