package com.github.phillbarber.service.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import ratpack.rx.RxRatpack;
import rx.Observable;
import rx.observable.ListenableFutureObservable;

public class NonBlockingHotDAO implements DAO {

    private Session session;

    public NonBlockingHotDAO(Session session) {
        this.session = session;
    }
    //See https://github.com/ReactiveX/RxJavaGuava
    public Observable<String> getValueFromDB() {
        ResultSetFuture resultSetFuture = session.executeAsync(new SimpleStatement("select dummy from dummy"));
        Observable<ResultSet> resultSetObservable = ListenableFutureObservable.from(resultSetFuture, RxRatpack.scheduler());
        return resultSetObservable.map(resultSet -> resultSet.one().getString("dummy"));
    }
}
