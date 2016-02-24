package com.github.phillbarber.service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import rx.Observable;

public class BlockingCassandraDAO {

    private Session session;

    public BlockingCassandraDAO(Session session) {
        this.session = session;
    }

    public Observable<String> getRowFromDB() {
        ResultSetFuture resultSetFuture = session.executeAsync(new SimpleStatement("select dummy from dummy"));
        Observable<ResultSet> resultSetObservable = Observable.from(resultSetFuture);
        return resultSetObservable.map(resultSet -> resultSet.one().getString("dummy"));
    }
}
