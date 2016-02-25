package com.github.phillbarber.service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import ratpack.rx.RxRatpack;
import rx.Observable;

public class BlockingCassandraDAO {

    private Session session;

    public BlockingCassandraDAO(Session session) {
        this.session = session;
    }

//NonBlocking
//    public Observable<String> getRowFromDB() {
//        ResultSetFuture resultSetFuture = session.executeAsync(new SimpleStatement("select dummy from dummy"));
//        Observable<ResultSet> resultSetObservable = ListenableFutureObservable.from(resultSetFuture, Schedulers.computation());
//        return resultSetObservable.map(resultSet -> {
//            return resultSet.one().getString("dummy");
//        });
//    }


//Blocking
//    public Observable<String> getRowFromDB() {
//        ResultSetFuture resultSetFuture = session.executeAsync(new SimpleStatement("select dummy from dummy"));
//        Observable<ResultSet> resultSetObservable = Observable.from(resultSetFuture);
//        return resultSetObservable.map(resultSet -> resultSet.one().getString("dummy"));
//    }

//Non Blocking
    public Observable<String> getRowFromDB() {
        SimpleStatement statement = new SimpleStatement("select dummy from dummy");
        Observable<ResultSet> resultSetObservable1 = AsyncCassandraWrapper.executeAsyncOnSubscribe(statement, RxRatpack.scheduler(), session);

        //ResultSetFuture resultSetFuture = session.executeAsync(statement);
        //Observable<ResultSet> resultSetObservable = Observable.from(resultSetFuture);
        return resultSetObservable1.map(resultSet -> resultSet.one().getString("dummy"));
    }
}
