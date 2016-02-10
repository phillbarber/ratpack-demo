package com.github.phillbarber;

import rx.Observable;
import rx.Subscriber;

public class ObservableOnDifferentThreadService {


    public Observable<String> getContent() {

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                new Thread(() -> {
                    subscriber.onNext("First Value");
                    subscriber.onCompleted();
                }).start();
            }
        });
    }
}
