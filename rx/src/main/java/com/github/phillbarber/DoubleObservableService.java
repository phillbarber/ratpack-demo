package com.github.phillbarber;

import rx.Observable;

public class DoubleObservableService {

    public Observable<String> getContent(){
        return Observable.just("First Value", "Second Value");
    }
}
