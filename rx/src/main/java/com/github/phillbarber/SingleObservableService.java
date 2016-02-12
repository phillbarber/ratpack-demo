package com.github.phillbarber;

import rx.Observable;

public class SingleObservableService {

    public Observable<String> getContent() {
        return Observable.just("One and only value");
    }
}
