package com.github.phillbarber.scenario.doubleobservable;

import rx.Observable;
import rx.Subscriber;

public class DoubleObservableService {



    public Observable<String> getContent(){
//
//        return Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        subscriber.onNext("First Value");
//                        subscriber.onNext("Second Value");
//                        subscriber.onCompleted();
//                    }
//                }).start();
//
//            }
//        });
        return Observable.just("First Value", "Second Value");
    }

}
