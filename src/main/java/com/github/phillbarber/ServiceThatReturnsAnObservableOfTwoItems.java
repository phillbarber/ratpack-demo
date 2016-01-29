package com.github.phillbarber;

import rx.Observable;

public class ServiceThatReturnsAnObservableOfTwoItems {

    public Observable<String> getObservable(){
        return Observable.just("ONE", "TWO");
    }

}
