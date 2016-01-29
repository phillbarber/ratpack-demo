package ratpack.example.java;

import rx.Observable;

/**
 * Created by pergola on 28/01/16.
 */
public class ServiceThatReturnsAnObservableOfTwoItems {

    public Observable<String> getObservable(){
        return Observable.just("ONE", "TWO");
    }

}
