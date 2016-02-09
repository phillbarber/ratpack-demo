package com.github.phillbarber.scenario.doubleobservable;

import rx.Observable;
import rx.Subscriber;

public class DoubleObservableService {

    public Observable<String> getContent(){
        return Observable.just("First Value", "Second Value");
    }
}
